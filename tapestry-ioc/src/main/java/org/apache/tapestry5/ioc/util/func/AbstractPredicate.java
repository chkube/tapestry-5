// Copyright 2010 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.ioc.util.func;

import org.apache.tapestry5.ioc.internal.util.Defense;

public abstract class AbstractPredicate<T> implements Predicate<T>
{
    public Predicate<T> and(final Predicate<? super T> other)
    {
        Defense.notNull(other, "other");

        final Predicate<T> left = this;

        return new AbstractPredicate<T>()
        {
            public boolean accept(T object)
            {
                return left.accept(object) && other.accept(object);
            };
        };
    }

    public Predicate<T> or(final Predicate<? super T> other)
    {
        Defense.notNull(other, "other");

        final Predicate<T> left = this;

        return new AbstractPredicate<T>()
        {
            public boolean accept(T object)
            {
                return left.accept(object) || other.accept(object);
            };
        };
    }

    public Predicate<T> invert()
    {
        final Predicate<T> normal = this;

        return new AbstractPredicate<T>()
        {
            public boolean accept(T object)
            {
                return !normal.accept(object);
            };
        };
    }

}