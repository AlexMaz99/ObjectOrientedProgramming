package agh.cs.map;

import agh.cs.structures.Vector2d;

public interface IPositionChangeObserver {
    void positionChanged (Vector2d oldPosition, Vector2d newPosition);
}
