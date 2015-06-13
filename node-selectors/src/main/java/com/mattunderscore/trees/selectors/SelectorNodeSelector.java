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

package com.mattunderscore.trees.selectors;

import java.util.Iterator;

import com.mattunderscore.trees.matchers.AlwaysMatcher;
import com.mattunderscore.trees.selection.NodeSelector;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Selector that applies a selector to the child nodes selected by another selector.
 * @param <E> The element type
 */
public final class SelectorNodeSelector<E> implements NodeSelector<E> {
    private final NodeSelector<E> baseSelector;
    private final NodeSelector<E> extensionSelector;

    public SelectorNodeSelector(NodeSelector<E> baseSelector, NodeSelector<E> extensionSelector) {
        this.baseSelector = baseSelector;
        this.extensionSelector = extensionSelector;
    }

    @Override
    public <N extends Node<E>> Iterator<N> select(Tree<E, N> tree) {
        final Iterator<N> startingPoints = baseSelector.select(tree);
        return new NodeSelectionIterator<>(new NodeChildrenIterator<>(startingPoints, new AlwaysMatcher<>()), extensionSelector);
    }
}