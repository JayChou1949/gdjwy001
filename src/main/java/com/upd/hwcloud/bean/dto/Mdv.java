package com.upd.hwcloud.bean.dto;


import com.upd.hwcloud.bean.entity.iaasConfig.MoveDataVideo;

public class Mdv {

    private MoveDataVideo move;
    private MoveDataVideo data;
    private MoveDataVideo video;

    public MoveDataVideo getMove() {
        return move;
    }

    public void setMove(MoveDataVideo move) {
        this.move = move;
    }

    public MoveDataVideo getData() {
        return data;
    }

    public void setData(MoveDataVideo data) {
        this.data = data;
    }

    public MoveDataVideo getVideo() {
        return video;
    }

    public void setVideo(MoveDataVideo video) {
        this.video = video;
    }
}
