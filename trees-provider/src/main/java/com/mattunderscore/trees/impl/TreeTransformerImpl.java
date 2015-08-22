/* Copyright © 2015 Matthew Champion
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of mattunderscore.com nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL MATTHEW CHAMPION BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.mattunderscore.trees.impl;

import com.mattunderscore.trees.binary.MutableBinaryTree;
import com.mattunderscore.trees.binary.OpenMutableBinaryTreeNode;
import com.mattunderscore.trees.impl.suppliers.impl.RootReferenceFactorySupplier;
import com.mattunderscore.trees.impl.suppliers.impl.RotatorSupplier;
import com.mattunderscore.trees.spi.Rotator;
import com.mattunderscore.trees.spi.RootReferenceFactory;
import com.mattunderscore.trees.transformation.RotationDirection;

/**
 * Provides tree transformation operations.
 * @author Matt Champion on 22/08/2015
 */
public final class TreeTransformerImpl {
    private final RootReferenceFactorySupplier rootReferenceFactorySupplier;
    private final RotatorSupplier rotatorSupplier;

    public TreeTransformerImpl(RootReferenceFactorySupplier rootReferenceFactorySupplier, RotatorSupplier rotatorSupplier) {
        this.rootReferenceFactorySupplier = rootReferenceFactorySupplier;
        this.rotatorSupplier = rotatorSupplier;
    }

    public <E, N extends OpenMutableBinaryTreeNode<E, N>> void rotateRootInPlace(MutableBinaryTree<E, N> tree, RotationDirection direction) {
        if (tree.isEmpty()) {
            throw new IllegalArgumentException("An empty tree cannot be rotated");
        }

        final N root = tree.getRoot();
        final RootReferenceFactory<E, N> referenceFactory = rootReferenceFactorySupplier.get(root);
        final Rotator.RootReference<N> reference = referenceFactory.wrapTree(tree);

        final Rotator<E, N> rotator = rotatorSupplier.get(root, direction);
        rotator.rotate(reference, root);
    }

    public <E, N extends OpenMutableBinaryTreeNode<E, N>> void rotateInPlace(N rootParent, N rotationRoot, RotationDirection direction) {

        final RootReferenceFactory<E, N> referenceFactory = rootReferenceFactorySupplier.get(rootParent);
        final Rotator.RootReference<N> reference = referenceFactory.wrapNode(rootParent);

        final Rotator<E, N> rotator = rotatorSupplier.get(rotationRoot, direction);
        rotator.rotate(reference, rotationRoot);
    }
}
