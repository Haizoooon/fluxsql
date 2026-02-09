package de.haizon.flux.types;

public enum DatabaseTypes {

    VARCHAR(255),
    INT(-1),
    BIGINT(-1);

    final int length;

     DatabaseTypes(int length){
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
