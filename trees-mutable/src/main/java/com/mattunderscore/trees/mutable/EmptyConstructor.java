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

package com.mattunderscore.trees.mutable;

import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.simple.collections.FixedUncheckedSimpleCollection;

/**
 * Implementation of {@link com.mattunderscore.trees.spi.EmptyTreeConstructor} for
 * {@link com.mattunderscore.trees.mutable.MutableTreeImpl}.
 * @author Matt Champion on 28/01/15.
 */
public final class EmptyConstructor<E> implements EmptyTreeConstructor<E, MutableSettableNode<E>, MutableTreeImpl<E>> {

    @Override
    public final MutableTreeImpl<E> build() {
        return new MutableTreeImpl<>(null, new FixedUncheckedSimpleCollection<>(new Object[0]));
    }

    @Override
    public Class<? extends Tree> forClass() {
        return MutableTreeImpl.class;
    }
}
