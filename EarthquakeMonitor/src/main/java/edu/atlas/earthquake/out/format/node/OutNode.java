package edu.atlas.earthquake.out.format.node;

import edu.atlas.earthquake.entity.Earthquake;

public interface OutNode {
    public String getOut(Earthquake earthquake);
}
