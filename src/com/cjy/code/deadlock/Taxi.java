package com.cjy.code.deadlock;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.HashSet;
import java.util.Set;

public class Taxi {
    private Point            location, destination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public synchronized Point getLocation() {
        return location;
    }

    public synchronized void SetLocation(Point location) {
        this.location = location;
        if (location.equals(destination)) {
            dispatcher.notidyAcailable(this);
        }
    }
}

class Dispatcher {

    private final Set<Taxi> taxis;
    private final Set<Taxi> acailableTaxis;

    public Dispatcher() {
        taxis = new HashSet<>();
        acailableTaxis = new HashSet<>();
    }

    public synchronized void notidyAcailable(Taxi taxi) {
        acailableTaxis.add(taxi);
    }

    public synchronized Image getImage() {
        Image image = new Image() {

            @Override
            public int getWidth(ImageObserver observer) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public ImageProducer getSource() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Object getProperty(String name, ImageObserver observer) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getHeight(ImageObserver observer) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public Graphics getGraphics() {
                // TODO Auto-generated method stub
                return null;
            }
        };

        return image;
    }
}
