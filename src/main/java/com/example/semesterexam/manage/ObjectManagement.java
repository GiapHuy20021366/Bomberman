package com.example.semesterexam.manage;

import com.example.semesterexam.core.Figure;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.core.Monster;
import com.example.semesterexam.core.Subject;
import com.example.semesterexam.core.Wall;
import com.example.semesterexam.lanscape.Grass;
import com.example.semesterexam.lanscape.SoftWall;
import com.example.semesterexam.weapon.Arrow;
import com.example.semesterexam.weapon.Boom;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ObjectManagement {
    HashMap<String, Figure> figures = new HashMap<>();
    HashMap<Point2D, Wall> walls = new HashMap<>();
    HashMap<Point2D, Grass> grasses = new HashMap<>();
    HashMap<String, Monster> monsters = new HashMap<>();
    List<Boom> booms = new ArrayList<>();
    GameScreen gameScreen;

    public void addBoom(Boom boom) {
        booms.add(boom);
    }

    public void addMonster(Monster monster) {
        monsters.put(monster.getName(), monster);
    }

    public void addGrass(Grass grass) {
        grasses.put(grass.point2D,grass);
    }


    public void removeBoom(Boom boom) {
        booms.remove(boom);
    }

    public void addCharacter(String name, Figure figure) {
        figures.put(name, figure);
    }

    public void addWall(Wall wall) {

        walls.put(wall.point2D, wall);

    }


    public boolean isOverlapping(Subject s, double newX, double newY) {
        return getOverlapping(s, newX, newY) != null;
    }

    public boolean isOverlapping(Subject s, double newX, double newY, String direction) {
        return getOverlapping(s, newX, newY, direction) != null;
    }

    public Subject getOverlapping(Subject s, double newX, double newY) {
        double dx = s.getFitWidth() / 20d;
        double dy = s.getFitHeight() / 20d;

        for (Point2D p : walls.keySet()) {

            Wall wall = walls.get(p);

            boolean isOverlapping = true;

            if (wall.getY() + wall.getFitHeight() < newY + dy) isOverlapping = false; // Wall on Top of Subject
            if (wall.getY() + dy > newY + s.getFitHeight()) isOverlapping = false; // Wall on bottom of Subject
            if (wall.getX() + wall.getFitWidth() < newX + dx) isOverlapping = false; // Wall on left of Subject
            if (wall.getX() + dx > newX + s.getFitWidth()) isOverlapping = false; // Wall on right of Subject

            if (isOverlapping) return wall;
        }
        return null;
    }

    public Subject getOverlapping(Subject s, double newX, double newY, String direction) {
        int x = (int) ((newX + s.getFitWidth() / 2d) / gameScreen.getComponentSize());
        int y = (int) ((newY + s.getFitHeight() / 2d) / gameScreen.getComponentSize());

        Wall[] walls_ = new Wall[3];
        switch (direction) {
            case "RIGHT":
                x++;
                walls_[0] = walls.get(new Point2D(x, y));
                walls_[1] = walls.get(new Point2D(x, y - 1));
                walls_[2] = walls.get(new Point2D(x, y + 1));
                break;
            case "LEFT":
                x--;
                walls_[0] = walls.get(new Point2D(x, y));
                walls_[1] = walls.get(new Point2D(x, y - 1));
                walls_[2] = walls.get(new Point2D(x, y + 1));
                break;
            case "UP":
                y--;
                walls_[0] = walls.get(new Point2D(x, y));
                walls_[1] = walls.get(new Point2D(x - 1, y));
                walls_[2] = walls.get(new Point2D(x + 1, y));
                break;
            case "DOWN":
                y++;
                walls_[0] = walls.get(new Point2D(x, y));
                walls_[1] = walls.get(new Point2D(x - 1, y));
                walls_[2] = walls.get(new Point2D(x + 1, y));
                break;
        }

        if (intersects(s, newX, newY, walls_[1])) return walls_[1];
        if (intersects(s, newX, newY, walls_[0])) return walls_[0];
        if (intersects(s, newX, newY, walls_[2])) return walls_[2];
        return null;
    }

    public boolean intersects(Subject s, double newX, double newY, Wall wall) {
        if (wall == null) return false;
        double dx = s.getFitWidth() / 10d;
        double dy = s.getFitHeight() / 10d;

        boolean isOverlapping = true;

        if (wall.getY() + wall.getFitHeight() < newY + dy) isOverlapping = false; // Wall on Top of Subject
        if (wall.getY() + dy > newY + s.getFitHeight()) isOverlapping = false; // Wall on bottom of Subject
        if (wall.getX() + wall.getFitWidth() < newX + dx) isOverlapping = false; // Wall on left of Subject
        if (wall.getX() + dx > newX + s.getFitWidth()) isOverlapping = false; // Wall on right of Subject

        if (isOverlapping) return true;
        return false;

    }

    public void resetDefaultSpeed(double rate) {
        for (String s : figures.keySet()) {
            figures.get(s).setDefaultSpeed(rate);
        }

        // Redraw Monster
        for (String m : monsters.keySet()) {
            monsters.get(m).setDefaultSpeed(rate);
        }
    }

    public void reDraw() {
        // Redraw wall
        for (Point2D p : walls.keySet()) {
            walls.get(p).draw();
        }
        // Redraw Character
        for (String s : figures.keySet()) {
            figures.get(s).draw();
        }

        // Redraw Boom
        for (Boom boom : booms) {
            boom.draw();
        }

        // Redraw Monster
        for (String m : monsters.keySet()) {
            monsters.get(m).draw();
        }

        // Redraw grass
        for (Point2D p : grasses.keySet()) {
            grasses.get(p).draw();
        }

    }

    public Point2D getNearestWall(Wall wall, String direction, String statusNow) {
        double x = wall.point2D.getX();
        double y = wall.point2D.getY();
        double y_ = (statusNow.equals("UP")) ? y + 1.0d : y - 1.0d;
        double x_ = (statusNow.equals("RIGHT")) ? x - 1.0d : x + 1.0d;
        switch (direction) {
            case "RIGHT":
                x += 1.0d;
                for (; x < gameScreen.getMap().getMAX_COLUMN(); x++) {
                    Point2D p1 = new Point2D(x, y);
                    Wall wall_1 = walls.get(p1);
                    Point2D p2 = new Point2D(x, y_);
                    Wall wall_2 = walls.get(p2);
                    if (wall_2 != null) return null;
                    else {
                        if (wall_1 == null) return p1;
                    }

                }
                break;
            case "LEFT":
                x -= 1.0d;
                for (; x >= 0; x--) {
                    Point2D p1 = new Point2D(x, y);
                    Wall wall_1 = walls.get(p1);
                    Point2D p2 = new Point2D(x, y_);
                    Wall wall_2 = walls.get(p2);
                    if (wall_2 != null) return null;
                    else {
                        if (wall_1 == null) return p1;
                    }
                }
                break;
            case "UP":
                y -= 1.0d;
                for (; y >= 0; y--) {
                    Point2D p1 = new Point2D(x, y);
                    Wall wall_1 = walls.get(p1);
                    Point2D p2 = new Point2D(x_, y);
                    Wall wall_2 = walls.get(p2);
                    if (wall_2 != null) return null;
                    else {
                        if (wall_1 == null) return p1;
                    }
                }
                break;
            case "DOWN":
                y += 1.0d;
                for (; y < gameScreen.getMap().getMAX_ROW(); y++) {
                    Point2D p1 = new Point2D(x, y);
                    Wall wall_1 = walls.get(p1);
                    Point2D p2 = new Point2D(x_, y);
                    Wall wall_2 = walls.get(p2);
                    if (wall_2 != null) return null;
                    else {
                        if (wall_1 == null) return p1;
                    }
                }
                break;
        }
        return null;
    }

    public Figure getCollision(Monster m) {
        for (String s : figures.keySet()) {
            if (figures.get(s).getLayoutBounds().intersects(m.getLayoutBounds())) {
                return figures.get(s);
            }
        }
        return null;
    }

    public Character intersect(Monster m, double newX, double newY) {
        for (String s : figures.keySet()) {
            Character c = figures.get(s);

            if ((c.getX() - newX) * (c.getX() - newX)
                    + (c.getY() - newY) * (c.getY() - newY)
                    <= gameScreen.getComponentSize() * gameScreen.getComponentSize() * 0.5d) return c;
        }
        return null;
    }

    public Character intersect(Monster m) {
        for (String s : figures.keySet()) {
            Character c = figures.get(s);
            if ((c.getX() - m.getX()) * (c.getX() - m.getX())
                    + (c.getY() - m.getY()) * (c.getY() - m.getY())
                    <= gameScreen.getComponentSize() * gameScreen.getComponentSize()) return c;
        }
        return null;
    }


    public List<Subject> subjects(Boom boom, double range) {
        return subjects(boom, range, range, range, range);
    }

    public List<Subject> subjects(Boom boom, double rangeTop, double rangeDown, double rangeLeft, double rangeRight) {
        List<Subject> subjects = new ArrayList<>();

        double dx = boom.getFitWidth() / 3d;
        double dy = boom.getFitHeight() / 3d;

        int minX = (int) ((boom.getX() + dx) / gameScreen.getComponentSize());
        int maxX = (int) ((boom.getX() + boom.getFitWidth() - dx) / gameScreen.getComponentSize());
        int minY = (int) ((boom.getY() + dy) / gameScreen.getComponentSize());
        int maxY = (int) ((boom.getY() + boom.getFitHeight() - dy) / gameScreen.getComponentSize());

        double max_X_Range = boom.getX() + rangeRight * gameScreen.getComponentSize();
        double min_X_Range = boom.getX() - (rangeLeft - 1) * gameScreen.getComponentSize();
        double min_Y_Range = boom.getY() - (rangeTop - 1) * gameScreen.getComponentSize();
        double max_Y_Range = boom.getY() + rangeDown * gameScreen.getComponentSize();

        // Right
        for (double i = (boom.getX() + boom.getFitWidth() / 2d) / gameScreen.getComponentSize(); i <= max_X_Range / gameScreen.getComponentSize(); i += 1d) {
            Point2D p1 = new Point2D((int) i, minY);
            Wall wall1 = walls.get(p1);
            if (wall1 != null) {
                if (wall1.getClass() == SoftWall.class) {
                    subjects.add(grasses.get(p1));
                    subjects.add(wall1);
                }
                else {
                    max_X_Range = ((int) i) * gameScreen.getComponentSize();
                    break;
                }
            } else subjects.add(grasses.get(p1));
            if (minY != maxY) {
                Point2D p2 = new Point2D((int) i, maxY);
                Wall wall2 = walls.get(p2);
                if (wall2 != null) {
                    if (wall2.getClass() == SoftWall.class) {
                        subjects.add(grasses.get(p2));
                        subjects.add(wall2);
                    }
                    else {
                        max_X_Range = ((int) i) * gameScreen.getComponentSize();
                        break;
                    }
                } else subjects.add(grasses.get(p2));
            }
        }

        // Left
        for (double i = (boom.getX() + boom.getFitWidth() / 2d) / gameScreen.getComponentSize() - 1d; i >= min_X_Range / gameScreen.getComponentSize(); i -= 1d) {
            Point2D p1 = new Point2D((int) i, minY);
            Wall wall1 = walls.get(p1);
            if (wall1 != null) {
                if (wall1.getClass() == SoftWall.class) {
                    subjects.add(grasses.get(p1));
                    subjects.add(wall1);
                }
                else {
                    min_X_Range = ((int) i + 1) * gameScreen.getComponentSize();
                    break;
                }
            } else subjects.add(grasses.get(p1));
            if (minY != maxY) {
                Point2D p2 = new Point2D((int) i, maxY);
                Wall wall2 = walls.get(p2);
                if (wall2 != null) {
                    if (wall2.getClass() == SoftWall.class) {
                        subjects.add(grasses.get(p2));
                        subjects.add(wall2);
                    }
                    else {
                        min_X_Range = ((int) i + 1) * gameScreen.getComponentSize();
                        break;
                    }
                } else subjects.add(grasses.get(p2));
            }
        }

        // Down
        for (double i = (boom.getY() + boom.getFitHeight() / 2d) / gameScreen.getComponentSize(); i <= max_Y_Range / gameScreen.getComponentSize(); i += 1d) {
            Point2D p1 = new Point2D(minX, (int) i);
            Wall wall1 = walls.get(p1);
            if (wall1 != null) {
                if (wall1.getClass() == SoftWall.class) {
                    subjects.add(grasses.get(p1));
                    subjects.add(wall1);
                }
                else {
                    max_Y_Range = ((int) i) * gameScreen.getComponentSize();
                    break;
                }
            } else subjects.add(grasses.get(p1));
            if (minX != maxX) {
                Point2D p2 = new Point2D(maxX, (int) i);
                Wall wall2 = walls.get(p2);
                if (wall2 != null) {
                    if (wall2.getClass() == SoftWall.class) {
                        subjects.add(grasses.get(p2));
                        subjects.add(wall2);
                    }
                    else {
                        max_Y_Range = ((int) i) * gameScreen.getComponentSize();
                        break;
                    }
                } else subjects.add(grasses.get(p2));
            }

        }

        // Up
        for (double i = (boom.getY() + boom.getFitHeight() / 2d) / gameScreen.getComponentSize() - 1d; i >= min_Y_Range / gameScreen.getComponentSize(); i -= 1d) {
            Point2D p1 = new Point2D(minX, (int) i);
            Wall wall1 = walls.get(p1);
            if (wall1 != null) {
                if (wall1.getClass() == SoftWall.class) {
                    subjects.add(grasses.get(p1));
                    subjects.add(wall1);
                }
                else {
                    min_Y_Range = ((int) i + 1) * gameScreen.getComponentSize();
                    break;
                }
            } else subjects.add(grasses.get(p1));
            if (minX != maxX) {
                Point2D p2 = new Point2D(maxX, (int) i);
                Wall wall2 = walls.get(p2);
                if (wall2 != null) {
                    if (wall2.getClass() == SoftWall.class) {
                        subjects.add(grasses.get(p2));
                        subjects.add(wall2);
                    }
                    else {
                        min_Y_Range = ((int) i + 1) * gameScreen.getComponentSize();
                        break;
                    }
                } else subjects.add(grasses.get(p2));
            }
        }

        Rectangle2D row = new Rectangle2D(min_X_Range, boom.getY(), Math.max(max_X_Range - min_X_Range, 0), gameScreen.getComponentSize());

        Rectangle2D col = new Rectangle2D(boom.getX(), min_Y_Range, gameScreen.getComponentSize(), Math.max(max_Y_Range - min_Y_Range, 0));

        boom.setRowPow(row);
        boom.setColPow(col);

        double space = gameScreen.getComponentSize() / 5d; // Have a space at four sides of subjects

        // Monster
        for (String m : monsters.keySet()) {
            Monster monster = monsters.get(m);
            if (monster.getLayoutBounds().intersects(row.getMinX() + space, row.getMinY() + space, row.getWidth() - 2d * space, row.getHeight() - 2d * space)
                    || monster.getLayoutBounds().intersects(col.getMinX() + space, col.getMinY() + space, col.getWidth() - 2d * space, col.getHeight() - 2d * space)) {
                subjects.add(monster);
            }
        }

        // Characters
        for (String c : figures.keySet()) {
            Character character = figures.get(c);
            if (character.getLayoutBounds().intersects(row.getMinX() + space, row.getMinY() + space, row.getWidth() - 2d * space, row.getHeight() - 2d * space)
                    || character.getLayoutBounds().intersects(col.getMinX() + space, col.getMinY() + space, col.getWidth() - 2d * space, col.getHeight() - 2d * space)) {
                subjects.add(character);
//                System.out.println(character.getName());
            }
        }

        // Booms
        for (Boom b : booms) {
            if (b.equals(boom)) continue;

            if (b.getLayoutBounds().intersects(row.getMinX() + space, row.getMinY() + space, row.getWidth() - 2d * space, row.getHeight() - 2d * space)
                    || b.getLayoutBounds().intersects(col.getMinX() + space, col.getMinY() + space, col.getWidth() - 2d * space, col.getHeight() - 2d * space)) {
                subjects.add(b);
            }
        }

        return subjects;


    }

    public void removeWall(Wall wall) {
        walls.remove(wall.point2D);
        gameScreen.getMap().getChildren().remove(wall);
    }

    public void removeMonsterOutOfManage(Monster m) {
        monsters.remove(m.getName());
    }

    public void removeMonster(Monster m) {
        monsters.remove(m.getName());
        gameScreen.getMap().getChildren().remove(m);
    }

    public void removeCharacter(Character c) {
        figures.remove(c.getName());
        gameScreen.getMap().getChildren().remove(c);
    }

    public Subject arrowIntersect(Arrow arrow, double newX, double newY) {
        // Walls

        double dx = arrow.getFitWidth() / 2d;
        double dy = arrow.getFitHeight() / 2d;
//        if (arrow.dx > 0) dx = arrow.getFitWidth();
//        else if (arrow.dx < 0) dx = -arrow.getFitWidth();
//        else if (arrow.dy > 0) dy = arrow.getFitHeight();
//        else dy = - arrow.getFitHeight();

        int x = (int) ((newX + dx) / gameScreen.getComponentSize());
        int y = (int) ((newY + dy) / gameScreen.getComponentSize());

        Wall wall = walls.get(new Point2D(x, y));
        if (wall != null) {
//            System.out.println("Wall");
            return wall;
        }

        // Monster
        for (String m : monsters.keySet()) {
            Monster monster = monsters.get(m);
            if (monster.getLayoutBounds().intersects(newX, newY, arrow.widthReality, arrow.heightReality))
                return monster;
        }

        // Booms
        for (Boom boom : booms) {
            if (boom.getLayoutBounds().intersects(newX, newY, arrow.widthReality, arrow.heightReality)) return boom;
        }

        return null;

    }

    public void setBoom(Boom boom) {

    }
}
