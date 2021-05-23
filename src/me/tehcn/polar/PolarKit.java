package me.tehcn.polar;

import java.util.List;

public class PolarKit {

    private PolarItem[] kitItems;

    public PolarKit(String name, List<PolarItem> items) {
        int i = 0;
        items.forEach(item -> {
            kitItems[i] = item;
        });
    }
    public PolarItem[] getKitItems() {
        return kitItems;
    }
}
