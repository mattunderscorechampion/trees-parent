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

package com.mattunderscore.trees.matchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mattunderscore.trees.base.ImmutableNode;
import com.mattunderscore.trees.selection.NodeMatcher;
import com.mattunderscore.trees.tree.ClosedNode;
import com.mattunderscore.trees.tree.Node;

/**
 * Unit tests for EqualityMatcher.
 * @author Matt Champion on 20/12/14
 */
public final class EqualityMatcherTest {

    @Test
    public void matches() {
        final Node<String, ClosedNode<String>> node = new ImmutableNode<String, ClosedNode<String>>("a", new Object[0]) {};
        final NodeMatcher<String> matcher = new EqualityMatcher<>("a");
        assertTrue(matcher.matches(node));
    }

    @Test
    public void notMatches() {
        final Node<String, ClosedNode<String>> node = new ImmutableNode<String, ClosedNode<String>>("a", new Object[0]) {};
        final NodeMatcher<String> matcher = new EqualityMatcher<>("b");
        assertFalse(matcher.matches(node));
    }

    @Test
    public void testEquals() {
        final NodeMatcher<String> matcher0 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher1 = new EqualityMatcher<>("a");

        assertTrue(matcher0.equals(matcher1));
        assertTrue(matcher1.equals(matcher0));
        assertEquals(matcher0.hashCode(), matcher1.hashCode());
    }

    @Test
    public void testNotEquals0() {
        final NodeMatcher<String> matcher0 = new EqualityMatcher("a");
        final NodeMatcher<String> matcher1 = new EqualityMatcher("b");

        assertFalse(matcher0.equals(matcher1));
    }

    @Test
    public void testNotEquals1() {
        final NodeMatcher<String> matcher0 = new EqualityMatcher<>("a");

        assertFalse(matcher0.equals(null));
    }

    @Test
    public void testNotEquals2() {
        final NodeMatcher<String> matcher0 = new EqualityMatcher<>("a");

        assertFalse(matcher0.equals(new Object()));
    }
}
