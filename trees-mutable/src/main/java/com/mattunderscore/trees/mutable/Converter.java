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

package com.mattunderscore.trees.mutable;

import java.util.Iterator;

import com.mattunderscore.trees.spi.TreeConverter;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.simple.collections.ArrayListSimpleCollection;

/**
 * Implementation of {@link com.mattunderscore.trees.spi.TreeConverter} for
 * {@link com.mattunderscore.trees.mutable.MutableTreeImpl}.
 * @author Matt Champion on 28/01/15.
 */
public final class Converter<E> implements TreeConverter<E, MutableSettableNode<E>, MutableTreeImpl<E>> {

    @Override
    public final <S extends OpenNode<E, S>> MutableTreeImpl<E> build(Tree<E, S> sourceTree) {
        final S root = sourceTree.getRoot();

        return duplicate(root.getElement(), root.childIterator(), root.getNumberOfChildren());
    }

    private <S extends OpenNode<E, S>> MutableTreeImpl<E> duplicate(
            E element, Iterator<? extends S> childIterator, int numberOfChildren) {
        final ArrayListSimpleCollection<MutableTreeImpl<E>> children = new ArrayListSimpleCollection<>(numberOfChildren);
        while (childIterator.hasNext()) {
            final S child = childIterator.next();
            children.add(duplicate(child.getElement(), child.childIterator(), child.getNumberOfChildren()));
        }
        return new MutableTreeImpl<>(element, children);
    }

    @Override
    public Class<? extends Tree> forClass() {
        return MutableTreeImpl.class;
    }
}
