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

package com.mattunderscore.trees.construction;

import java.util.Comparator;

import com.mattunderscore.trees.sorted.SortedTreeBuilder;
import com.mattunderscore.trees.sorted.SortingAlgorithm;
import com.mattunderscore.trees.sorted.SortingTreeBuilder;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Factory for tree builders.
 * @author Matt Champion on 11/09/14.
 */
public interface TreeBuilderFactory {
    /**
     * Obtain a {@link TopDownTreeRootBuilder}.
     * @param <E> The element type of the tree
     * @return Top down tree builder
     */
    <E, N extends OpenNode<E, N>> TopDownTreeRootBuilder<E, N> topDownBuilder();

    /**
     * Obtain a {@link BottomUpTreeBuilder}.
     * @param <E> The element type of the tree
     * @return Bottom up tree builder
     */
    <E, N extends OpenNode<E, N>> BottomUpTreeBuilder<E, N> bottomUpBuilder();

    /**
     * Obtain an {@link com.mattunderscore.trees.organised.OrganisedTreeBuilder} that creates sorting trees.
     * @param comparator The comparator used to sort the elements
     * @param <E> The element type of the tree
     * @return Sorting tree builder
     */
    <E, N extends OpenNode<E, N>> SortingTreeBuilder<E, N> sortingTreeBuilder(Comparator<E> comparator);

    /**
     * Obtain an {@link com.mattunderscore.trees.organised.OrganisedTreeBuilder} that creates sorting trees. Uses the
     * element's comparison operations to sort the tree.
     * @param <E> The element type of the tree
     * @return Sorting tree builder
     */
    <E extends Comparable<E>, N extends OpenNode<E, N>> SortingTreeBuilder<E, N> sortingTreeBuilder();

    /**
     * Obtain an {@link com.mattunderscore.trees.organised.OrganisedTreeBuilder} that creates sorted trees. The
     * algorithm to place the nodes must be specified separately.
     * @param comparator The comparator used to sort the elements
     * @param algorithm The algorithm to sort the trees.
     * @param <E> The element type of the tree
     * @return Sorted tree builder
     */
    <E, N extends OpenNode<E, N>> SortedTreeBuilder<E, N> sortedTreeBuilder(Comparator<E> comparator, SortingAlgorithm algorithm);

    /**
     * Obtain an {@link com.mattunderscore.trees.organised.OrganisedTreeBuilder} that creates sorted trees. The
     * algorithm to place the nodes must be specified separately. Uses the element's comparison operations to sort the
     * tree.
     * @param algorithm The algorithm to sort the trees.
     * @param <E> The element type of the tree
     * @return Sorted tree builder
     */
    <E extends Comparable<E>, N extends OpenNode<E, N>> SortedTreeBuilder<E, N> sortedTreeBuilder(SortingAlgorithm algorithm);

    /**
     * Copy an existing tree.
     * @param tree The tree to copy
     * @param <E> The element type of the tree
     * @param <N> The node type of the tree to copy
     * @param <T> The tree type to copy
     * @return The copied tree
     */
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> T copy(T tree);

    /**
     * Copy an existing tree as a different type.
     * @param tree The tree to copy
     * @param klass The type of tree to create
     * @param <E> The element type of the tree
     * @param <N> The node type of the tree to copy
     * @param <N2> The node type of the tree to create
     * @param <T2> The tree type to create
     * @return The copied tree
     */
    <E, N extends OpenNode<E, N>, N2 extends OpenNode<E, N2>, T2 extends Tree<E, N2>> T2 copy(Tree<E, N> tree, Class<T2> klass);
}
