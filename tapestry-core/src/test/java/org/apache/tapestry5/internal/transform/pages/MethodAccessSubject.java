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

package org.apache.tapestry5.internal.transform.pages;

import java.sql.SQLException;

import org.apache.tapestry5.internal.services.InternalClassTransformationImplTest;

/**
 * Used by {@link InternalClassTransformationImplTest} for a number of tests related to
 * method access.
 */
public class MethodAccessSubject
{
    private String marker;

    public String getMarker()
    {
        return marker;
    }

    public void publicVoidNoArgs()
    {
        marker = "publicVoidNoArgs";
    }

    public void publicVoidNoArgsFail()
    {
        marker = "publicVoidNoArgsFail";

        throw new RuntimeException("Fail inside pvnoaf.");
    }

    public int incrementer(int input)
    {
        marker = "incrementer(" + input + ")";

        return input + 1;
    }

    public void publicVoidThrowsException() throws SQLException
    {
        marker = "publicVoidThrowsException";

        throw new SQLException("From publicVoidThrowsException()");
    }
}
