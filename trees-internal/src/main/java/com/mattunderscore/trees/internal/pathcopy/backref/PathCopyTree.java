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

package com.mattunderscore.trees.internal.pathcopy.backref;

import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.ids.IdGenerator;
import com.mattunderscore.trees.ids.impl.IdGeneratorImpl;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.spi.NodeToTreeConverter;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.spi.TreeConverter;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Path copy tree with a separate data structure to support back references.
 * @author Matt Champion on 04/11/14.
 */
public final class PathCopyTree<E> implements MutableTree<E, MutableNode<E>> {
    private static final IdGenerator ID_GENERATOR = new IdGeneratorImpl();
    private final AtomicReference<PathCopyNodeHolder<E>> rootHolderRef = new AtomicReference<>();

    @Override
    public MutableNode<E> setRoot(E root) {
        final PathCopyNode<E> newRoot = PathCopyNode.createRoot(ID_GENERATOR, root);
        rootHolderRef.set(newRoot.holder);
        return newRoot;
    }

    @Override
    public MutableNode<E> getRoot() {
        final PathCopyNodeHolder<E> holder = rootHolderRef.get();
        if (holder != null) {
            holder.lock();
            try {
                return holder.get();
            }
            finally {
                holder.unlock();
            }
        }
        else {
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        return rootHolderRef.get() == null;
    }

    public static final class EmptyConstructor<E> implements EmptyTreeConstructor<E, PathCopyTree<E>> {

        @Override
        public PathCopyTree<E> build() {
            return new PathCopyTree<>();
        }

        @Override
        public Class<?> forClass() {
            return PathCopyTree.class;
        }
    }

    public static final class NodeConverter<E> implements NodeToTreeConverter<E, MutableNode<E>, PathCopyTree<E>, Node<E>> {

        @Override
        public PathCopyTree<E> treeFromRootNode(Node<E> node) {
            final PathCopyTree<E> newTree = new PathCopyTree<>();
            newTree.setRoot(node.getElement());
            copyChildren(newTree.setRoot(node.getElement()), node.getChildren());
            return newTree;
        }

        private void copyChildren(MutableNode<E> newParent, SimpleCollection<? extends Node<E>> children) {
            for (final Node<E> child : children) {
                final MutableNode<E> newChild = newParent.addChild(child.getElement());
                copyChildren(newChild, child.getChildren());
            }
        }

        @Override
        public Class<?> forClass() {
            return PathCopyTree.class;
        }
    }

    public static final class Converter<E> implements TreeConverter<E, PathCopyTree<E>> {
        private final NodeConverter<E> converter = new NodeConverter();

        @Override
        public PathCopyTree<E> build(Tree<E, ? extends Node<E>> sourceTree) {
            final Node<E> root = sourceTree.getRoot();
            return converter.treeFromRootNode(root);
        }

        @Override
        public Class<?> forClass() {
            return PathCopyTree.class;
        }
    }

    public static final class Constructor<E> implements TreeConstructor<E, PathCopyTree<E>> {

        @Override
        public PathCopyTree<E> build(E e, PathCopyTree<E>[] subtrees) {
            final PathCopyTree<E> tree = new PathCopyTree<>();
            final MutableNode<E> root = tree.setRoot(e);
            for (PathCopyTree<E> subtree : subtrees) {
                final Node<E> subRoot = subtree.getRoot();
                final MutableNode<E> newSubRoot = root.addChild(subRoot.getElement());
                copyChildren(newSubRoot, subRoot.getChildren());
            }
            return tree;
        }

        @Override
        public Class<?> forClass() {
            return PathCopyTree.class;
        }

        private void copyChildren(MutableNode<E> newParent, SimpleCollection<? extends Node<E>> children) {
            for (final Node<E> child : children) {
                final MutableNode<E> newChild = newParent.addChild(child.getElement());
                copyChildren(newChild, child.getChildren());
            }
        }
    }
}