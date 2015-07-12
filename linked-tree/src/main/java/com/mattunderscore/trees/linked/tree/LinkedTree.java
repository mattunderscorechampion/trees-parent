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

package com.mattunderscore.trees.linked.tree;

import java.util.Iterator;

import net.jcip.annotations.NotThreadSafe;

import com.mattunderscore.trees.base.AbstractSettableNode;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.spi.NodeToRelatedTreeConverter;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.spi.TreeConverter;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.utilities.collections.ArrayListSimpleCollection;

/**
 * A simple tree implementation. Commonly used for temporary trees.
 * @author Matt Champion on 07/08/14.
 */
@NotThreadSafe
public final class LinkedTree<E> extends AbstractSettableNode<E, MutableSettableStructuredNode<E>> implements MutableTree<E, MutableSettableStructuredNode<E>>, MutableSettableStructuredNode<E> {
    private final ArrayListSimpleCollection<LinkedTree<E>> children;

    public LinkedTree(E root) {
        super(root);
        children = new ArrayListSimpleCollection<>();
    }

    @SuppressWarnings("unchecked")
    private LinkedTree(E root, LinkedTree[] subtrees) {
        super(root);
        children = new ArrayListSimpleCollection<>();
        for (final LinkedTree subtree : subtrees) {
            children.add(subtree);
        }
    }

    @Override
    public boolean isEmpty() {
        return elementReference.get() == null;
    }

    @Override
    public LinkedTree<E> getRoot() {
        if (isEmpty()) {
            return null;
        }
        else {
            return this;
        }
    }

    @Override
    public Iterator<LinkedTree<E>> childStructuralIterator() {
        return children.structuralIterator();
    }

    @Override
    public LinkedTree<E> getChild(int nChild) {
        return children.get(nChild);
    }

    @Override
    public LinkedTree<E> setChild(int nChild, E element) {
        final LinkedTree<E> child = new LinkedTree<>(element);
        children.set(nChild, child);
        return child;
    }

    @Override
    public LinkedTree<E> addChild(E e) {
        if (e == null) {
            throw new NullPointerException("You cannot add a child to an empty tree");
        }
        final LinkedTree<E> child = new LinkedTree<>(e);
        children.add(child);
        return child;
    }

    @Override
    public boolean removeChild(MutableSettableStructuredNode<E> child) {
        return child != null && child.getClass().equals(getClass()) && children.remove((LinkedTree<E>)child);
    }

    @Override
    public LinkedTree<E> setRoot(E root) {
        setElement(root);
        return this;
    }

    @Override
    public int getNumberOfChildren() {
        return children.size();
    }

    @Override
    public Iterator<LinkedTree<E>> childIterator() {
        return children.iterator();
    }

    public final static class NodeConverter<E> implements NodeToRelatedTreeConverter<E, MutableSettableStructuredNode<E>,
            LinkedTree<E>> {
        @Override
        public LinkedTree<E> treeFromRootNode(MutableSettableStructuredNode<E> node) {
            return (LinkedTree<E>)node;
        }

        @Override
        public Class<? extends OpenNode> forClass() {
            return LinkedTree.class;
        }
    }

    public final static class Constructor<E> implements TreeConstructor<E, MutableSettableStructuredNode<E>, LinkedTree<E>> {

        @Override
        @SafeVarargs
        public final LinkedTree<E> build(E e, LinkedTree<E>... subtrees) {
            return new LinkedTree<>(e, subtrees);
        }

        @Override
        public Class<? extends Tree> forClass() {
            return LinkedTree.class;
        }
    }

    public final static class EmptyConstructor<E> implements EmptyTreeConstructor<E, MutableSettableStructuredNode<E>, LinkedTree<E>> {

        @Override
        public LinkedTree<E> build() {
            return new LinkedTree<>(null);
        }

        @Override
        public Class<? extends Tree> forClass() {
            return LinkedTree.class;
        }
    }

    public final static class Converter<E> implements TreeConverter<E, MutableSettableStructuredNode<E>, LinkedTree<E>> {
        @Override
        public <S extends OpenNode<E, S>> LinkedTree<E> build(Tree<E, S> sourceTree) {
            final S root = sourceTree.getRoot();
            final LinkedTree<E> newTree = new LinkedTree<>(root.getElement());
            final Iterator<? extends S> iterator = root.childIterator();
            while (iterator.hasNext()) {
                duplicate(newTree, iterator.next());
            }
            return newTree;
        }

        @Override
        public Class<? extends Tree> forClass() {
            return LinkedTree.class;
        }

        private <S extends OpenNode<E, S>> void duplicate(LinkedTree<E> newParent, S sourceChild) {
            final LinkedTree<E> newChild = newParent.addChild(sourceChild.getElement());
            final Iterator<? extends S> iterator = sourceChild.childIterator();
            while (iterator.hasNext()) {
                duplicate(newChild, iterator.next());
            }
        }
    }

    /**
     * Construct a TypeKey for a specific element type.
     * @param <E> The element type
     * @return The type key
     */
    public static <E> TypeKey<LinkedTree<E>> typeKey() {
        return new TypeKey<LinkedTree<E>>() {};
    }
}
