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
import org.opengis.referencing.FactoryException;

import static org.junit.Assert.*;

public class FunctionEditorsTest extends RasterTestBase {

    @Test
    public void testSetValuesWithEmptyRaster() throws FactoryException {
        GridCoverage2D emptyRaster = RasterConstructors.makeEmptyRaster(1, 5, 5, 0, 0, 1, -1, 0, 0, 0);
        double[] values = new double[] {1,1,1,0,0,0,1,2,3,3,5,6,7,0,0,3,0,0,3,0,0,0,0,0,0};
        emptyRaster = MapAlgebra.addBandFromArray(emptyRaster, values, 1, 0d);
        double[] newValues = new double[] {11,12,13,14,15,16,17,18,19};
        GridCoverage2D raster = PixelFunctionEditors.setValues(emptyRaster, 1, 2, 2, 3, 3, newValues, true);
        double[] actual = MapAlgebra.bandAsArray(raster, 1);
        double[] expected = new double[] {1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 11.0, 12.0, 13.0, 3.0, 5.0, 14.0, 15.0, 0.0, 0.0, 3.0, 0.0, 0.0, 19.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        assertArrayEquals(actual, expected, 0.0);

        raster = PixelFunctionEditors.setValues(emptyRaster, 1, 2, 2, 3, 3, newValues);
        actual = MapAlgebra.bandAsArray(raster, 1);
        expected = new double[] {1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 11.0, 12.0, 13.0, 3.0, 5.0, 14.0, 15.0, 16.0, 0.0, 3.0, 17.0, 18.0, 19.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        assertArrayEquals(actual, expected, 0.0);
    }

    @Test
    public void testSetValueWithEmptyRaster() throws FactoryException {
        GridCoverage2D emptyRaster = RasterConstructors.makeEmptyRaster(1, 5, 5, 0, 0, 1, -1, 0, 0, 0);
        double[] values = new double[] {1,1,1,0,0,0,1,2,3,3,5,6,7,0,0,3,0,0,3,0,0,0,0,0,0};
        emptyRaster = MapAlgebra.addBandFromArray(emptyRaster, values, 1, 0d);
        double newValue = 1777;
        GridCoverage2D raster = PixelFunctionEditors.setValue(emptyRaster, 1, 2, 2, newValue);
        double[] actual = MapAlgebra.bandAsArray(raster, 1);
        double[] expected = new double[]{1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1777.0, 2.0, 3.0, 3.0, 5.0, 6.0, 7.0, 0.0, 0.0, 3.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        assertArrayEquals(expected, actual, 0.1d);

        newValue = 8723;
        raster = PixelFunctionEditors.setValue(emptyRaster, 2, 2, newValue);
        actual = MapAlgebra.bandAsArray(raster, 1);
        expected = new double[]{1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 8723.0, 2.0, 3.0, 3.0, 5.0, 6.0, 7.0, 0.0, 0.0, 3.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        assertArrayEquals(expected, actual, 0.1d);
    }
}
