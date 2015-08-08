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

package com.mattunderscore.trees.walkers;

import java.util.Iterator;
import java.util.Stack;

import com.mattunderscore.trees.traversal.Walker;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.OpenStructuralNode;
import com.mattunderscore.trees.tree.Tree;
import net.jcip.annotations.Immutable;

/**
 * @author Matt Champion on 17/08/14.
 */
@Immutable
public final class PostOrderWalker {

    public PostOrderWalker() {
    }

    public <E, N extends OpenNode<E, N>> void traverseTree(Tree<E, N> tree, Walker<N> walker) {
        if (tree.isEmpty()) {
            walker.onEmpty();
            walker.onCompleted();
        }
        else {
            final Stack<State<E, N>> parents = new Stack<>();
            N current = tree.getRoot();

            while (!parents.isEmpty() || current != null) {
                if (current != null) {
                    final State<E, N> state = new State<>(current);
                    parents.push(state);
                    if (state.iterator.hasNext()) {
                        current = state.iterator.next();
                    }
                    else {
                        // Leaf
                        current = null;
                    }
                }
                else {
                    final State<E, N> state = parents.peek();
                    if (state.iterator.hasNext()) {
                        current = state.iterator.next();
                        continue;
                    }

                    if (!state.iterator.hasNext()) {
                        parents.pop();
                    }

                    if (!walker.onNext(state.node)) {
                        return;
                    }
                }
            }

            walker.onCompleted();
        }
    }

    private static final class State<E, N extends OpenNode<E, N>> {
        private final N node;
        private final Iterator<? extends N> iterator;

        @SuppressWarnings("unchecked")
        public State(N node) {
            this.node = node;
            if (node instanceof OpenStructuralNode) {
                final OpenStructuralNode structuralNode = (OpenStructuralNode)node;
                this.iterator = structuralNode.childStructuralIterator();
            }
            else {
                this.iterator = node.childIterator();
            }
        }
    }
}
