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

package com.mattunderscore.trees.immutable;

import java.util.Iterator;

import com.mattunderscore.trees.spi.TreeConverter;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Implementation of {@link com.mattunderscore.trees.spi.TreeConverter} for
 * {@link com.mattunderscore.trees.immutable.TreeNodeImpl}.
 * @author Matt Champion on 28/01/15.
 */
public final class Converter<E> implements TreeConverter<E, Node<E>, Tree<E, Node<E>>> {

    @SuppressWarnings("unchecked")
    @Override
    public final <S extends OpenNode<E, S>> Tree<E, Node<E>> build(Tree<E, S> sourceTree) {
        final S root = sourceTree.getRoot();
        final TreeNodeImpl<E>[] newChildren = duplicateChildren(root);
        return new TreeNodeImpl(root.getElement(), newChildren);
    }

    private <S extends OpenNode<E, S>> TreeNodeImpl<E>[] duplicateChildren(OpenNode<E, S> parent) {
        @SuppressWarnings("unchecked")
        final TreeNodeImpl<E>[] newChildren = new TreeNodeImpl[parent.getNumberOfChildren()];
        int i = 0;
        final Iterator<? extends OpenNode<E, S>> iterator = parent.childIterator();
        while (iterator.hasNext()) {
            final OpenNode<E, S> sourceChild = iterator.next();
            final TreeNodeImpl[] newGrandChildren = duplicateChildren(sourceChild);
            newChildren[i] = new TreeNodeImpl<>(sourceChild.getElement(), newGrandChildren);
            i++;
        }
        return newChildren;
    }

    @Override
    public Class<? extends Tree> forClass() {
        return TreeNodeImpl.class;
    }
}
