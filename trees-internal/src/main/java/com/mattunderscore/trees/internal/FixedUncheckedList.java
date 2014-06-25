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

import java.lang.reflect.Array;
import java.util.*;

/**
 * Array backed, not typed checked, list implementation for immutable lists from a trusted source.
 * @author matt on 20/06/14.
 */
public final class FixedUncheckedList<E> implements List<E> {
    private final Object[] array;

    public FixedUncheckedList(Object[] array) {
        this.array = array;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        return new FLIterator();
    }

    @Override
    public Object[] toArray() {
        return toArray((E[])Array.newInstance(array.getClass().getComponentType(), array.length));
    }

    @Override
    public <T> T[] toArray(final T[] targetArray) {
        final Class<?> componentType = array.getClass().getComponentType();
        final E[] destArray;
        if (targetArray == null) {
            throw new NullPointerException();
        }
        else if (!targetArray.getClass().getComponentType().equals(componentType)) {
            throw new ArrayStoreException();
        }
        else if (targetArray.length < array.length) {
            destArray = (E[])Array.newInstance(componentType, array.length);
        }
        else {
            destArray = (E[])targetArray;
        }
        int i = 0;
        for (;i < array.length; i++) {
            destArray[i] = (E)array[i];
        }
        for (;i < destArray.length; i++) {
            destArray[i] = null;
        }
        return (T[])destArray;
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(int index) {
        return (E)array[index];
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new FLIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        else {
            return new FLIterator(index);
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private final class FLIterator implements ListIterator<E> {
        private int pos;

        public FLIterator() {
            pos = 0;
        }

        public FLIterator(int index) {
            pos = index;
        }

        @Override
        public boolean hasNext() {
            return pos < array.length;
        }

        @Override
        public E next() {
            if (pos < array.length) {
                return (E) array[pos++];
            }
            else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public boolean hasPrevious() {
            return pos > 0;
        }

        @Override
        public E previous() {
            if (pos > 0) {
                return (E) array[--pos];
            }
            else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public int nextIndex() {
            return pos + 1;
        }

        @Override
        public int previousIndex() {
            return pos - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }
}
