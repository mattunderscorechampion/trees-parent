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

import java.util.function.Predicate;

import org.junit.Test;

import com.mattunderscore.trees.base.ImmutableNode;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.OpenNode;

/**
 * Unit tests for IdentityMatcher.
 * @author Matt Champion on 25/12/14
 */
public final class IdentityMatcherTest {
    @Test
    public void matches() {
        final String element = "a";
        final OpenNode<String, Node<String>> node = new ImmutableNode<String, Node<String>>(element, new Object[0]) {};
        final Predicate<OpenNode<? extends String, ?>> matcher = new IdentityMatcher<>(element);
        assertTrue(matcher.test(node));
    }

    @Test
    public void notMatches() {
        final OpenNode<String, Node<String>> node = new ImmutableNode<String, Node<String>>(new String("a"), new Object[0]) {};
        final Predicate<OpenNode<? extends String, ?>> matcher = new IdentityMatcher<>(new String("a"));
        assertFalse(matcher.test(node));
    }

    @Test
    public void testEquals() {
        final String element = "a";
        final Predicate<OpenNode<? extends String, ?>> matcher0 = new IdentityMatcher<>(element);
        final Predicate<OpenNode<? extends String, ?>> matcher1 = new IdentityMatcher<>(element);

        assertTrue(matcher0.equals(matcher1));
        assertTrue(matcher1.equals(matcher0));
        assertEquals(matcher0.hashCode(), matcher1.hashCode());
    }

    @Test
    public void testEqualsSelf() {
        final String element = "a";
        final Predicate<OpenNode<? extends String, ?>> matcher0 = new IdentityMatcher<>(element);

        assertTrue(matcher0.equals(matcher0));
    }

    @Test
    public void testNotEquals0() {
        final Predicate<OpenNode<? extends String, ?>> matcher0 = new IdentityMatcher<>(new String("a"));
        final Predicate<OpenNode<? extends String, ?>> matcher1 = new IdentityMatcher<>(new String("a"));

        assertFalse(matcher0.equals(matcher1));
    }

    @Test
    public void testNotEquals1() {
        final Predicate<OpenNode<? extends String, ?>> matcher0 = new IdentityMatcher<>(new String("a"));

        assertFalse(matcher0.equals(null));
    }

    @Test
    public void testNotEquals2() {
        final Predicate<OpenNode<? extends String, ?>> matcher0 = new IdentityMatcher<>(new String("a"));

        assertFalse(matcher0.equals(new Object()));
    }
}
