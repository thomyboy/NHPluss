package de.hitec.nhplus.model;

import javafx.beans.property.SimpleLongProperty;

public class Room {
    private SimpleLongProperty roomID;
    private String roomName;

    public Room(long roomID, String roomName) {
        this.roomID = new SimpleLongProperty(roomID);
        this.roomName = roomName;
    }
    public Room( String roomName) {
        this.roomName = roomName;
    }

    public long getRoomID() {
        return roomID.get();
    }


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + roomID +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
