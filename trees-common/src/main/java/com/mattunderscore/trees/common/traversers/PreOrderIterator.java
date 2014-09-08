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

import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.Tree;
import com.mattunderscore.trees.utilities.iterators.PrefetchingIterator;
import net.jcip.annotations.NotThreadSafe;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author matt on 17/08/14.
 */
@NotThreadSafe
public final class PreOrderIterator<E , N extends Node<E>, T extends Tree<E, N>> extends PrefetchingIterator<N> {
    private final Stack<N> parents = new Stack<>();
    private N current;

    public PreOrderIterator(T tree) {
        current = tree.getRoot();
        parents.push(current);
    }

    @Override
    protected N calculateNext() throws NoSuchElementException {
        if (!parents.isEmpty()) {
            final N n = current;
            final Collection<N> children = (Collection<N>) n.getChildren();
            final N[] reversed = (N[]) Array.newInstance(n.getClass(), children.size());
            final Iterator<N> childIterator = children.iterator();
            for (int i = children.size() - 1; i >= 0; i--) {
                reversed[i] = childIterator.next();
            }
            for (N child : reversed) {
                parents.push(child);
            }
            current = parents.pop();
            return n;
        }
        throw new NoSuchElementException();
    }
}
