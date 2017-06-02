package com.gln.codenum1.chapter6.model;

import org.litepal.crud.DataSupport;

/**
 * Created by guolina on 2017/6/2.
 */
public class Introduction extends DataSupport {

    private int id;
    private String guide;
    private String digest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append("id=" + id)
                .append(", guide=" + guide)
                .append(", digest=" + digest)
                .append("]");
        return builder.toString();
    }
}
