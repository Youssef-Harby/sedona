/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sedona.common.raster;

import org.geotools.coverage.grid.GridCoverage2D;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RasterOutputTest
        extends RasterTestBase {

    @Test
    public void testAsBase64() throws IOException {
        GridCoverage2D raster = rasterFromGeoTiff(resourceFolder + "raster/raster_with_no_data/test5.tiff");
        String resultRaw = RasterOutputs.asBase64(raster);
        assertTrue(resultRaw.startsWith("iVBORw0KGgoAAAANSUhEUgAABaAAAALQCAMAAABR+ye1AAADAFBMVEXE9/W48vOq7PGa5u6L3"));
    }

    @Test
    public void testAsGeoTiff() throws IOException {
        GridCoverage2D rasterOg = rasterFromGeoTiff(resourceFolder + "raster/test1.tiff");
        GridCoverage2D rasterTest = RasterConstructors.fromGeoTiff(RasterOutputs.asGeoTiff(rasterFromGeoTiff(resourceFolder + "raster/test1.tiff")));
        assert(rasterTest != null);
        assertEquals(rasterTest.getEnvelope().toString(), rasterOg.getEnvelope().toString());
    }

    @Test
    public void testAsGeoTiffWithCompressionTypes() throws IOException {
        GridCoverage2D rasterOg = rasterFromGeoTiff(resourceFolder + "raster/test1.tiff");
        byte[] rasterBytes1 = RasterOutputs.asGeoTiff(rasterOg, "LZW", 1.0);
        byte[] rasterBytes2 = RasterOutputs.asGeoTiff(rasterOg, "Deflate", 0.5);
        GridCoverage2D rasterNew = RasterConstructors.fromGeoTiff(rasterBytes1);
        assertEquals(rasterOg.getEnvelope().toString(), rasterNew.getEnvelope().toString());
        assert(rasterBytes1.length > rasterBytes2.length);
    }
}
