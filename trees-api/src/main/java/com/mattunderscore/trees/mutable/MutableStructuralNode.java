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

import java.util.Iterator;

import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.tree.StructuralNode;

/**
 * Mutable structural node. Unlike {@link MutableNode} child nodes are placed at specific positions.
 * @author Matt Champion on 30/01/15
 */
public interface MutableStructuralNode<E> extends StructuralNode<E> {
      @Override
      Iterator<? extends MutableStructuralNode<E>> childIterator();

      @Override
      Iterator<? extends MutableStructuralNode<E>> childStructuralIterator();

      @Override
      MutableStructuralNode<E> getChild(int nChild);

      /**
       * Set the nth child node.
       * <p>
       * Replaces any node already there.
       * @param nChild The nth value
       * @param element The element
       * @return The new node
       */
      MutableStructuralNode<E> setChild(int nChild, E element);
}
