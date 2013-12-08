package edu.atlas.earthquake.out;

import edu.atlas.earthquake.entity.Earthquake;

public interface OutNode {
    public String getOut(Earthquake earthquake);
}
