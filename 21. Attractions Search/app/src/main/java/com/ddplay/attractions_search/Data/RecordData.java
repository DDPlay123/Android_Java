package com.ddplay.attractions_search.Data;

public class RecordData {
    // data
    private String name;
    private String vicinity;

    public RecordData(
            String name,
            String vicinity)
    {
        this.name = name;
        this.vicinity = vicinity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
