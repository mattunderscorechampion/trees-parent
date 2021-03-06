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

package com.mattunderscore.trees.binary.search;

import java.util.Comparator;

import net.jcip.annotations.NotThreadSafe;

import com.mattunderscore.trees.binary.BinaryTree;
import com.mattunderscore.trees.binary.BinaryTreeNode;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.binary.mutable.MutableBinaryTreeNodeImpl;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.sorted.SortingTree;

/**
 * A binary search tree implementation. This is a {@link BinaryTree} that is also a {@link SortingTree}. The tree is
 * mutated by adding new elements to the tree and allowing the tree to place the nodes.
 * @author Matt Champion on 06/09/14.
 */
@NotThreadSafe
public final class BinarySearchTree<E> implements BinaryTree<E, BinaryTreeNode<E>>, SortingTree<E, BinaryTreeNode<E>> {
    private final Comparator<E> comparator;
    private MutableBinaryTreeNodeImpl<E> root;

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public synchronized BinarySearchTree<E> addElement(E element) {
        if (isEmpty()) {
            root = new MutableBinaryTreeNodeImpl<>(element);
        }
        else {
            addTo(root, element);
        }
        return this;
    }

    private void addTo(MutableBinaryTreeNode<E> node, E element) {
        final int comparison = comparator.compare(node.getElement(), element);
        if (comparison >= 0) {
            final MutableBinaryTreeNode<E> left = node.getLeft();
            if (left == null) {
                node.setLeft(element);
            }
            else {
                addTo(left, element);
            }
        }
        else {
            final MutableBinaryTreeNode<E> right = node.getRight();
            if (right == null) {
                node.setRight(element);
            }
            else {
                addTo(right, element);
            }
        }
    }

    @Override
    public synchronized BinaryTreeNode<E> getRoot() {
        return new WrappedBinaryNode<>(root);
    }

    @Override
    public synchronized boolean isEmpty() {
        return root == null;
    }

    /**
     * Construct a TypeKey for a specific element type.
     * @param <E> The element type
     * @return The type key
     */
    public static <E> TypeKey<BinarySearchTree<E>> typeKey() {
        return new TypeKey<BinarySearchTree<E>>() {};
    }

    @Override
    public Comparator<E> getComparator() {
        return comparator;
    }
}
