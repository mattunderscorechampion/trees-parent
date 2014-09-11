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

package com.mattunderscore.trees.internal;

import com.mattunderscore.trees.MutableNode;
import com.mattunderscore.trees.MutableTree;
import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.SimpleCollection;
import com.mattunderscore.trees.utilities.ArrayListSimpleCollection;
import com.mattunderscore.trees.utilities.CopyOnWriteSimpleCollection;

/**
 * @author matt on 11/09/14.
 */
public class PathCopyTree<E> implements MutableTree<E, MutableNode<E>> {
    private PathCopyTreeNode<E> root;

    public PathCopyTree() {
        root = null;
    }

    public PathCopyTree(E element) {
        root = new PathCopyTreeNode<>(element);
    }

    @Override
    public synchronized void setRoot(E root) {
        this.root = new PathCopyTreeNode<>(root);
    }

    @Override
    public synchronized MutableNode<E> getRoot() {
        return root;
    }

    @Override
    public synchronized boolean isEmpty() {
        return root == null;
    }

    private final static class PathCopyTreeNode<E> implements MutableNode<E> {
        private final PathCopyTreeNode<E> parent;
        private final E element;
        private CopyOnWriteSimpleCollection<MutableNode<E>> elementList;

        private PathCopyTreeNode(E element) {
            this.element = element;
            parent = null;
            elementList = new CopyOnWriteSimpleCollection<>();
        }

        private PathCopyTreeNode(PathCopyTreeNode<E> parent, E element) {
            this.element = element;
            this.parent = parent;
            elementList = new CopyOnWriteSimpleCollection<>();
        }

        @Override
        public E getElement() {
            return element;
        }

        @Override
        public Class<E> getElementClass() {
            return (Class<E>)element.getClass();
        }

        @Override
        public SimpleCollection<? extends MutableNode<E>> getChildren() {
            return elementList;
        }

        @Override
        public boolean isLeaf() {
            return elementList.isEmpty();
        }

        @Override
        public boolean removeChild(MutableNode<E> child) {
            return elementList.remove(child);
        }

        @Override
        public MutableNode<E> addChild(E e) {
            final MutableNode<E> node = new PathCopyTreeNode<E>(e);
            elementList.add(node);
            return node;
        }
    }
}
