package com.github.davidmoten.rtreemulti;

import com.github.davidmoten.rtreemulti.geometry.Geometry;
import com.github.davidmoten.rtreemulti.geometry.HasGeometry;
import com.github.davidmoten.rtreemulti.geometry.Rectangle;

public class Mbr implements HasGeometry {

    private final Rectangle r;

    public Mbr(Rectangle r) {
        this.r = r;
    }

    @Override
    public Geometry geometry() {
        return r;
    }

}
