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

package com.mattunderscore.trees.utilities;

import com.mattunderscore.trees.Children;
import com.mattunderscore.trees.OptionalEnumeration;
import com.mattunderscore.trees.utilities.iterators.PrefetchingIterator;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Array backed, not typed checked, Children implementation for immutable Children from a trusted source.
 * <p>This is immutable assuming the ownership of the backing array is exclusive.</p>
 * @author matt on 20/06/14.
 */
public final class FixedUncheckedChildren<E> implements Children<E> {
    private final Object[] array;

    public FixedUncheckedChildren(Object[] array) {
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
    public E get(int i) {
        return (E) array[i];
    }

    @Override
    public OptionalEnumeration<E> optionalEnumeration() {
        return new FUCOptionalEnumeration();
    }

    @Override
    public Iterator<E> iterator() {
        return new FUCIterator();
    }

    private final class FUCIterator extends PrefetchingIterator<E> {
        private int pos;

        public FUCIterator() {
            pos = 0;
        }

        @Override
        protected E calculateNext() throws NoSuchElementException {
            while (pos < array.length) {
                final E next = (E) array[pos++];
                if (next != null) {
                    return next;
                }
            }
            throw new NoSuchElementException();
        }
    }

    private final class FUCOptionalEnumeration implements OptionalEnumeration<E> {
        private int pos;

        public FUCOptionalEnumeration() {
            pos = 0;
        }

        @Override
        public boolean hasMoreElements() {
            return pos < array.length;
        }

        @Override
        public E nextElement() {
            return (E) array[pos++];
        }
    }
}