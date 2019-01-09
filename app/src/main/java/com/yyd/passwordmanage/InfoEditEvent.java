package com.yyd.passwordmanage;

public class InfoEditEvent {
    private int position;
    private Info info;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public InfoEditEvent(int position, Info info) {
        this.position = position;

        this.info = info;
    }
}
