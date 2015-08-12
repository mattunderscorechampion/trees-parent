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

package com.mattunderscore.trees.linked.tree;

import java.util.Iterator;

import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.spi.NodeToRelatedTreeConverter;
import com.mattunderscore.trees.spi.TreeConverter;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Implementation of {@link TreeConverter} for {@link LinkedTree}.
 * @author Matt Champion on 12/08/2015
 */
public final class Converter<E> implements TreeConverter<E, MutableSettableStructuredNode<E>, LinkedTree<E>> {
    @Override
    public <S extends OpenNode<E, S>> LinkedTree<E> build(Tree<E, S> sourceTree) {
        final S root = sourceTree.getRoot();
        final LinkedTree<E> newTree = new LinkedTree<>(root.getElement());
        final Iterator<? extends S> iterator = root.childIterator();
        while (iterator.hasNext()) {
            duplicate(newTree, iterator.next());
        }
        return newTree;
    }

    @Override
    public Class<? extends Tree> forClass() {
        return LinkedTree.class;
    }

    private <S extends OpenNode<E, S>> void duplicate(LinkedTree<E> newParent, S sourceChild) {
        final LinkedTree<E> newChild = newParent.addChild(sourceChild.getElement());
        final Iterator<? extends S> iterator = sourceChild.childIterator();
        while (iterator.hasNext()) {
            duplicate(newChild, iterator.next());
        }
    }
}
