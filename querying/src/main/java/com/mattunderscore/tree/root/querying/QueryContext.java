/* Copyright © 2016 Matthew Champion
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

package com.mattunderscore.tree.root.querying;

import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.utilities.ComparableComparator;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import static java.util.stream.Collectors.minBy;

/**
 * @author Matt Champion on 27/04/16
 */
public final class QueryContext<E, N extends OpenNode<E, N>> {

    public int height(N node) {
        if (node == null) {
            throw new NullPointerException("Null has no paths");
        }

        final Set<BackPath<E,N>> backPaths = BackPathsFromLeaves.backPathsFromLeavesOf(node);

        final Optional<Integer> maxDepth = backPaths
                .stream()
                .map(BackPath::getDepth)
                .collect(minBy(new ComparableComparator<>()));

        if (maxDepth.isPresent()) {
            return maxDepth.get();
        }
        else {
            throw new IllegalStateException("No paths found");
        }
    }
}
