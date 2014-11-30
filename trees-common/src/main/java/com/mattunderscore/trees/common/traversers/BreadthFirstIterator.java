/* Copyright © 2014 Matthew Champion
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

package com.mattunderscore.trees.common.traversers;

import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.spi.IteratorRemoveHandler;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.utilities.iterators.EmptyIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Matt Champion on 05/09/14.
 */
public final class BreadthFirstIterator<E , N extends Node<E>, T extends Tree<E, ? extends N>> extends RemoveHandlerIterator<E, N, T> {
    private Iterator<N> currentLayer;
    private List<N> nextLayer;


    public BreadthFirstIterator(T tree, IteratorRemoveHandler<E, N, T> handler) {
        super(tree, handler);
        currentLayer = new EmptyIterator<>();
        nextLayer = new ArrayList<>(1);
        nextLayer.add(tree.getRoot());
    }

    @Override
    protected N calculateNext() throws NoSuchElementException {
        if (currentLayer.hasNext()) {
            N next;
            do {
                next = currentLayer.next();
            } while (next == null);

            final SimpleCollection<N> children = (SimpleCollection<N>)next.getChildren();
            for (final N child : children) {
                nextLayer.add(child);
            }
            return next;
        }
        else {
            currentLayer = nextLayer.iterator();
            nextLayer = new ArrayList<>();

            N next;
            do {
                next = currentLayer.next();
            } while (next == null);

            final SimpleCollection<N> children = (SimpleCollection<N>)next.getChildren();
            for (final N child : children) {
                nextLayer.add(child);
            }
            return next;
        }
    }
}
