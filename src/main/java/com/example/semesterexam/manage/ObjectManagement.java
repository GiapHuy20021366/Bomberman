package com.example.semesterexam.manage;

import com.example.semesterexam.core.*;
import com.example.semesterexam.core.Character;
import com.example.semesterexam.effect.FreezeTime;
import com.example.semesterexam.item.*;
import com.example.semesterexam.lanscape.Gate;
import com.example.semesterexam.lanscape.Grass;
import com.example.semesterexam.lanscape.SoftWall;
import com.example.semesterexam.monster.*;
import com.example.semesterexam.tool.AstraPath;
import com.example.semesterexam.weapon.Boom;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;


import java.io.IOException;
import java.util.*;
import java.util.Map;


public class ObjectManagement {
    private final HashMap<String, Figure> figures = new HashMap<>();
    private final HashMap<Point2D, Wall> walls = new HashMap<>();
    private final HashMap<Point2D, Grass> grasses = new HashMap<>();
    private final HashMap<String, Monster> monsters = new HashMap<>();
    private final HashSet<Item> items = new HashSet<>();
    private final List<Boom> booms = new ArrayList<>();
    private GameScreen gameScreen;
    private final BooleanProperty access = new SimpleBooleanProperty(true);
    private Gate gate;
    private MiniMap miniMap;

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public void setMiniMap(MiniMap miniMap) {
        this.miniMap = miniMap;
    }

    public int count() {
        return figures.size() + walls.size() + monsters.size() + booms.size();
    }

    public Gate getGate() {
        return gate;
    }

    public void clear() {
        dieAll();
        walls.clear();
        grasses.clear();
        monsters.clear();
        items.clear();
        booms.clear();
    }

    public void hideAll() {
        dieAll();
        monsters.clear();
        booms.clear();
    }

    public boolean isAccess() {
        return access.get();
    }

    public void setAccess(boolean ac) {
        access.set(ac);
    }


    public void dieAll() {
        for (Map.Entry<String, Monster> m : monsters.entrySet()) {
            m.getValue().freeze();
//            m.getValue().die();
        }
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void addBoom(Boom boom) {
        booms.add(boom);

        if (miniMap != null) {
            miniMap.addBooms(boom);
        }
    }

    public void addMonster(Monster monster) {
        monsters.put(monster.getName(), monster);

        if (miniMap != null) {
            miniMap.addMonsters(monster);
        }
    }

    public void addCharacter(Figure figure) {
        figures.put(figure.getName(), figure);
    }

    public void addWall(Wall wall) {
        walls.put(wall.point2D, wall);
    }

    public void addGrass(Grass grass) {
        grasses.put(grass.point2D, grass);
    }


    public void removeBoom(Boom boom) {
        booms.remove(boom);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeWallOutOfManage(Wall wall) {
        walls.remove(wall.point2D);
    }

    public void removeWall(Wall wall) {
        walls.remove(wall.point2D);
        gameScreen.getMap().getChildren().remove(wall);

        try {
            randomItem(wall);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeMonsterOutOfManage(Monster m) {
        monsters.remove(m.getName());
        try {
            randomItem(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFigureOutOfManage(Figure figure) {
        figures.remove(figure.getName());
    }

    public void removeMonster(Monster m) {
        monsters.remove(m.getName());
        gameScreen.getMap().getChildren().remove(m);

    }

    public void removeCharacter(Character c) {
        if (c instanceof Figure) {
            figures.remove(c.getName());
            gameScreen.getMap().getChildren().remove(c);
        } else if (c instanceof Monster) {
            removeMonster((Monster) c);
        }

    }

    public boolean isOverlapping(Subject s, double newX, double newY) {
        return getOverlapping(s, newX, newY) != null;
    }

    public boolean isOverlapping(Subject s, double newX, double newY, Direction direction) {
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

    public Subject getOverlapping(Subject s, double newX, double newY, Direction direction) {

        // Check intersect walls
        int x = (int) ((newX + s.getFitWidth() / 2d) / gameScreen.getComponentSize());
        int y = (int) ((newY + s.getFitHeight() / 2d) / gameScreen.getComponentSize());

        Wall[] walls_ = new Wall[3];
        switch (direction) {
            case RIGHT -> {
                x++;
                walls_[0] = walls.get(new Point2D(x, y));
                walls_[1] = walls.get(new Point2D(x, y - 1));
                walls_[2] = walls.get(new Point2D(x, y + 1));
            }
            case LEFT -> {
                x--;
                walls_[0] = walls.get(new Point2D(x, y));
                walls_[1] = walls.get(new Point2D(x, y - 1));
                walls_[2] = walls.get(new Point2D(x, y + 1));
            }
            case UP -> {
                y--;
                walls_[0] = walls.get(new Point2D(x, y));
                walls_[1] = walls.get(new Point2D(x - 1, y));
                walls_[2] = walls.get(new Point2D(x + 1, y));
            }
            case DOWN -> {
                y++;
                walls_[0] = walls.get(new Point2D(x, y));
                walls_[1] = walls.get(new Point2D(x - 1, y));
                walls_[2] = walls.get(new Point2D(x + 1, y));
            }
        }

        if (intersects(s, newX, newY, walls_[1])) return walls_[1];
        if (intersects(s, newX, newY, walls_[0])) return walls_[0];
        if (intersects(s, newX, newY, walls_[2])) return walls_[2];


        // Check intersect booms
        double d = s.getFitWidth() / 3d;

        Rectangle2D rec = null;
        Rectangle2D recUp = new Rectangle2D(newX + d, newY, s.getFitWidth() - 2 * d, 1);
        Rectangle2D recDown = new Rectangle2D(newX + d, newY + s.getFitHeight() - 1, s.getFitWidth() - 2 * d, 1);
        Rectangle2D recLeft = new Rectangle2D(newX, newY + d, 1, s.getFitHeight() - 2 * d);
        Rectangle2D recRight = new Rectangle2D(newX + s.getFitWidth() - 1, newY + d, 1, s.getFitHeight() - 2 * d);
        switch (direction) {
            case UP -> {
                rec = recUp;
            }
            case DOWN -> {
                rec = recDown;
            }
            case LEFT -> {
                rec = recLeft;
            }
            case RIGHT -> {
                rec = recRight;
            }
        }

        for (Boom boom : booms) {
            if (boom.getLayoutBounds().intersects(rec.getMinX(), rec.getMinY(), rec.getWidth(), rec.getHeight())) {
                return boom;
            }
        }


        // Check intersect Monster
        if (s instanceof Figure) {
            for (String m : monsters.keySet()) {
                Monster monster = monsters.get(m);
                if (monster.getLayoutBounds().intersects(newX + d, newY + d, s.getFitWidth() - 2 * d, s.getFitHeight() - 2 * d)) {
                    return monster;
                }
            }
        }


        // Check intersect Figures
        if (s instanceof Monster m) {
            Rectangle2D range = null;
            switch (m.getOnDirection()) {
                case UP -> {
                    double maxRange = getMaxRange(recUp, m.getRangeFar(), Direction.UP);
                    range = new Rectangle2D(recUp.getMinX(), recUp.getMinY() - maxRange, recUp.getWidth(), recUp.getHeight() + maxRange);
                }
                case DOWN -> {
                    double maxRange = getMaxRange(recDown, m.getRangeFar(), Direction.DOWN);
                    range = new Rectangle2D(recDown.getMinX(), recDown.getMinY(), recDown.getWidth(), recDown.getHeight() + maxRange);
                }
                case LEFT -> {
                    double maxRange = getMaxRange(recLeft, m.getRangeFar(), Direction.LEFT);
                    range = new Rectangle2D(recLeft.getMinX() - maxRange, recLeft.getMinY(), recLeft.getWidth() + maxRange, recLeft.getHeight());
                }
                case RIGHT -> {
                    double maxRange = getMaxRange(recRight, m.getRangeFar(), Direction.RIGHT);
                    range = new Rectangle2D(recRight.getMinX(), recRight.getMinY(), recRight.getWidth() + maxRange, recRight.getHeight());
                }
            }
            if (range != null) {
                for (String c : figures.keySet()) {
                    Figure figure = figures.get(c);
                    if (figure.getLayoutBounds().intersects(range.getMinX(), range.getMinY(), range.getWidth(), range.getHeight())) {
                        return figure;
                    }
                }
            }
        }


        return null;
    }


    public double getMaxRange(Rectangle2D rec, double range, Direction direction) {
        double minX = rec.getMinX();
        double minY = rec.getMinY();
        double maxX = rec.getMaxX();
        double maxY = rec.getMaxY();
        switch (direction) {
            case UP -> {
                for (double i = minY; i > minY - range; i -= gameScreen.getComponentSize() / 2d) {
                    if (walls.get(new Point2D((int) (minX / gameScreen.getComponentSize()), (int) (i / gameScreen.getComponentSize()))) != null
                            || walls.get(new Point2D((int) (maxX / gameScreen.getComponentSize()), (int) (i / gameScreen.getComponentSize()))) != null) {
                        return Math.abs((i - minY));
                    }
                }
            }
            case DOWN -> {
                for (double i = minY; i <= minY + range; i += gameScreen.getComponentSize() / 2d) {
                    if (walls.get(new Point2D((int) (minX / gameScreen.getComponentSize()), (int) (i / gameScreen.getComponentSize()))) != null
                            || walls.get(new Point2D((int) (maxX / gameScreen.getComponentSize()), (int) (i / gameScreen.getComponentSize()))) != null) {
                        return Math.abs((i - minY));
                    }
                }
            }
            case LEFT -> {
                for (double i = minX; i >= minX - range; i -= gameScreen.getComponentSize() / 2d) {
                    if (walls.get(new Point2D((int) (i / gameScreen.getComponentSize()), (int) (minY / gameScreen.getComponentSize()))) != null
                            || walls.get(new Point2D((int) (i / gameScreen.getComponentSize()), (int) (maxY / gameScreen.getComponentSize()))) != null) {
                        return Math.abs((i - minX));
                    }
                }
            }
            case RIGHT -> {
                for (double i = minX; i <= minX + range; i++) {
                    if (walls.get(new Point2D((int) (i / gameScreen.getComponentSize()), (int) (minY / gameScreen.getComponentSize()))) != null
                            || walls.get(new Point2D((int) (i / gameScreen.getComponentSize()), (int) (maxY / gameScreen.getComponentSize()))) != null) {
                        return Math.abs((i - minX));
                    }
                }
            }
        }
        return range;
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

        return isOverlapping;

    }

    @Deprecated
    public void resetDefaultSpeed(double rate) {
        for (String s : figures.keySet()) {
            figures.get(s).setDefaultSpeed(rate);
        }

        // Redraw Monster
        for (String m : monsters.keySet()) {
            monsters.get(m).setDefaultSpeed(rate);
        }
    }

    @Deprecated
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

    public Point2D getNearestWall(Wall wall, Direction direction, Direction statusNow) {
        double x = wall.point2D.getX();
        double y = wall.point2D.getY();
        double y_ = (statusNow == Direction.UP) ? y + 1.0d : y - 1.0d;
        double x_ = (statusNow == Direction.RIGHT) ? x - 1.0d : x + 1.0d;
        switch (direction) {
            case RIGHT -> {
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
            }
            case LEFT -> {
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
            }
            case UP -> {
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
            }
            case DOWN -> {
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
            }
        }
        return null;
    }

    public Character characterIntersect(Monster m) {
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
                if (wall1 instanceof SoftWall) {
                    subjects.add(grasses.get(p1));
                    subjects.add(wall1);
                } else {
                    max_X_Range = ((int) i) * gameScreen.getComponentSize();
                    break;
                }
            } else subjects.add(grasses.get(p1));
            if (minY != maxY) {
                Point2D p2 = new Point2D((int) i, maxY);
                Wall wall2 = walls.get(p2);
                if (wall2 != null) {
                    if (wall2 instanceof SoftWall) {
                        subjects.add(grasses.get(p2));
                        subjects.add(wall2);
                    } else {
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
                if (wall1 instanceof SoftWall) {
                    subjects.add(grasses.get(p1));
                    subjects.add(wall1);
                } else {
                    min_X_Range = ((int) i + 1) * gameScreen.getComponentSize();
                    break;
                }
            } else subjects.add(grasses.get(p1));
            if (minY != maxY) {
                Point2D p2 = new Point2D((int) i, maxY);
                Wall wall2 = walls.get(p2);
                if (wall2 != null) {
                    if (wall2 instanceof SoftWall) {
                        subjects.add(grasses.get(p2));
                        subjects.add(wall2);
                    } else {
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
                if (wall1 instanceof SoftWall) {
                    subjects.add(grasses.get(p1));
                    subjects.add(wall1);
                } else {
                    max_Y_Range = ((int) i) * gameScreen.getComponentSize();
                    break;
                }
            } else subjects.add(grasses.get(p1));
            if (minX != maxX) {
                Point2D p2 = new Point2D(maxX, (int) i);
                Wall wall2 = walls.get(p2);
                if (wall2 != null) {
                    if (wall2 instanceof SoftWall) {
                        subjects.add(grasses.get(p2));
                        subjects.add(wall2);
                    } else {
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
                if (wall1 instanceof SoftWall) {
                    subjects.add(grasses.get(p1));
                    subjects.add(wall1);
                } else {
                    min_Y_Range = ((int) i + 1) * gameScreen.getComponentSize();
                    break;
                }
            } else subjects.add(grasses.get(p1));
            if (minX != maxX) {
                Point2D p2 = new Point2D(maxX, (int) i);
                Wall wall2 = walls.get(p2);
                if (wall2 != null) {
                    if (wall2 instanceof SoftWall) {
                        subjects.add(grasses.get(p2));
                        subjects.add(wall2);
                    } else {
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

        // Figures
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


    public Subject arrowIntersect(Bullet bullet, double newX, double newY) {
        // Walls

        double dx = bullet.getFitWidth() / 2d;
        double dy = bullet.getFitHeight() / 2d;

        if (bullet.dx > 0) {
            dx += bullet.widthReality / 2d;
        } else if (bullet.dx < 0) {
            dx = (bullet.getFitWidth() - bullet.widthReality) / 2d;
        } else if (bullet.dy > 0) {
            dy += bullet.heightReality / 2d;
        } else {
            dy = (bullet.getFitHeight() - bullet.heightReality) / 2d;
        }

        int x = (int) ((newX + dx) / gameScreen.getComponentSize());
        int y = (int) ((newY + dy) / gameScreen.getComponentSize());

        Wall wall = walls.get(new Point2D(x, y));
        if (wall != null) {
//            System.out.println("Wall");
            return wall;
        }

        // Monster
        if (bullet.getOwner() instanceof Figure) {
            for (String m : monsters.keySet()) {
                Monster monster = monsters.get(m);
                if (monster.getLayoutBounds().intersects(newX + (bullet.getFitWidth() - bullet.widthReality) / 2d,
                        newY + (bullet.getFitHeight() - bullet.heightReality) / 2d,
                        bullet.widthReality, bullet.heightReality)) {
                    return monster;
                }

            }
        }

        // Figures
        if (bullet.getOwner() instanceof Monster) {
            for (String c : figures.keySet()) {
                Figure figure = figures.get(c);
                if (figure.getLayoutBounds().intersects(newX + (bullet.getFitWidth() - bullet.widthReality) / 2d,
                        newY + (bullet.getFitHeight() - bullet.heightReality) / 2d,
                        bullet.widthReality, bullet.heightReality)) {
                    return figure;
                }
            }
        }


        // Booms
        for (Boom boom : booms) {
            if (boom.getLayoutBounds().intersects(newX + (bullet.getFitWidth() - bullet.widthReality) / 2d,
                    newY + (bullet.getFitHeight() - bullet.heightReality) / 2d,
                    bullet.widthReality, bullet.heightReality)) {
                return boom;
            }
        }

        return null;

    }

    public Grass[] getGrassAt(Bullet bullet) {
        double x = bullet.getX();
        double y = bullet.getY();

        double dx = bullet.getFitWidth() / 2d;
        double dy = bullet.getFitHeight() / 2d;

        int x_ = (int) ((bullet.getX() + dx) / gameScreen.getComponentSize());
        int y_ = (int) ((bullet.getY() + dy) / gameScreen.getComponentSize());

        int minX = (int) ((x + bullet.getFitWidth() * 0.2d) / gameScreen.getComponentSize());
        int maxX = (int) ((x + bullet.getFitWidth() * 0.8d) / gameScreen.getComponentSize());
        int minY = (int) ((y + bullet.getFitHeight() * 0.2d) / gameScreen.getComponentSize());
        int maxY = (int) ((y + bullet.getFitHeight() * 0.8d) / gameScreen.getComponentSize());

        if (bullet.dy != 0) {
            return new Grass[]{grasses.get(new Point2D(minX, y_)), grasses.get(new Point2D(maxX, y_))};
        } else {
            return new Grass[]{grasses.get(new Point2D(x_, minY)), grasses.get(new Point2D(x_, maxY))};
        }

//        int x = (int) ((bullet.getX() + dx) / gameScreen.getComponentSize());
//        int y = (int) ((bullet.getY() + dy) / gameScreen.getComponentSize());

//        return grasses.get(new Point2D(x, y));
    }

    public List<Monster> getMonsterEffectByIce(double x, double y, double range) {
        //
        List<Monster> list = new ArrayList<>();
        for (String m : monsters.keySet()) {
            Monster monster = monsters.get(m);
            if ((monster.getX() - x) * (monster.getX() - x) + (monster.getY() - y) * (monster.getY() - y) <= range * range) {
                list.add(monster);
            }
        }
        return list;
    }

    public Figure getFigureEarnItem(Item item) {
        for (String s : figures.keySet()) {
            if (figures.get(s).getLayoutBounds().intersects(item.getLayoutBounds())) {
                return figures.get(s);
            }
        }
        return null;
    }

    public void randomItem(Subject subject) throws IOException {
        int random = new Random().nextInt(9);

        Item item = null;
        switch (random) {
            case 1 -> {
                item = new BalloonArrow(subject.getX(), subject.getY(), gameScreen);
            }
            case 2 -> {
                item = new SpeedUp(subject.getX(), subject.getY(), gameScreen);
            }
            case 3 -> {
                item = new Immortal(subject.getX(), subject.getY(), gameScreen);
            }
            case 4 -> {
                item = new FasterPutBoom(subject.getX(), subject.getY(), gameScreen);
            }
            case 5 -> {
                item = new PlusHP(subject.getX(), subject.getY(), gameScreen);
            }
            case 6 -> {
                item = new StopTime(subject.getX(), subject.getY(), gameScreen);
            }
            case 7 -> {
                item = new IncreaseDamage(subject.getX(), subject.getY(), gameScreen);
            }
            case 8 -> {
                item = new PlusBullet(subject.getX(), subject.getY(), gameScreen);
            }
        }

        if (item != null) {
            gameScreen.getMap().getChildren().add(item);
            item.setDefault();
            item.startTimer();
            item.disappear();
        }
    }

    public void setDisableAllMonster(boolean b, long cycle) {
        for (String m : monsters.keySet()) {
            Monster monster = monsters.get(m);
//            monster.freeze();
            if (b) {
                try {
                    Effect effect = new FreezeTime(gameScreen, monster, cycle);
                    effect.setAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (b) {
                monster.freeze();
            } else {
                monster.outOfFreeze(1);
            }

//            monster.setIsDisableMoving(b);
//            monster.setDisableCauseDamage(b);
        }
    }

    public Monster randomMonster(GameScreen gameScreen) throws IOException {
        Monster m = null;
        int hex = new Random().nextInt(7);
        switch (hex) {
            case 0 -> {
                m = new Orc(gameScreen);
            }
            case 1 -> {
                m = new Satan(gameScreen);
            }
            case 2 -> {
                m = new Skeleton(gameScreen);
            }
            case 3 -> {
                m = new Winged(gameScreen);
            }
            case 4 -> {
                m = new Wolf(gameScreen);
            }
            case 5 -> {
                m = new Zombie(gameScreen);
            }
            case 6 -> {
                m = new BossHuMan(gameScreen);
            }
        }
        return m;
    }

    public Direction detectFigure(Monster m) {
        Point2D centerOfMonster = new Point2D(m.getX() + m.getFitWidth() / 2d, m.getY() + m.getFitHeight() / 2d);

        Point2D min = null;
        for (String fir : figures.keySet()) {
            Figure f = figures.get(fir);
            Point2D centerOfFigure = new Point2D(f.getX() + f.getFitWidth() / 2d, f.getY() + f.getFitHeight() / 2d);
            if (centerOfFigure.distance(centerOfMonster) <= m.getEyeFar()) {
                if (f.isSeen() || !checkIntersectWall(centerOfFigure, centerOfMonster)) {
                    f.setBeSeen();
                    if (min == null || centerOfMonster.distance(min) > centerOfMonster.distance(centerOfFigure)) {
                        min = centerOfFigure;
                    }
//                    boolean farType = m.getRangeFar() > gameScreen.getComponentSize();
//                    return nextDirection(centerOfMonster, centerOfFigure);
                }
            }
        }
        return nextDirection(centerOfMonster, min);
//        return null;
    }

    public Direction nextDirection(Point2D centerOfMonster, Point2D centerOfFigure) {
        if (centerOfMonster == null || centerOfFigure == null) {
            return null;
        }
        int[] from = parseInt(centerOfMonster);
        int[] to = parseInt(centerOfFigure);
        int[] nextCell = new AstraPath(from, to, gameScreen).next();
        if (nextCell == null) return null;
        if (from[0] == to[0] && from[1] == to[1]) {
//            System.out.println("Same");
            return nextDirectionOnSameCell(centerOfMonster, centerOfFigure);
        }
//        System.out.println("From " + from[0] + " " + from[1]);
//        System.out.println("To " + to[0] + " " + to[1]);
        return next(from, nextCell);
    }

    private int[] parseInt(Point2D p) {
        int i = (int) (p.getX() / gameScreen.getComponentSize());
        int j = (int) (p.getY() / gameScreen.getComponentSize());
        return new int[] {i, j};
    }

    private Direction next(int[] from, int[] to) {
        if (from[0] < to[0]) {
            return Direction.RIGHT;
        }
        if (from[0] > to[0]) {
            return Direction.LEFT;
        }
        if (from[1] < to[1]) {
            return Direction.DOWN;
        }
        if (from[1] > to[1]) {
            return Direction.UP;
        }
        return null;
    }


    public Direction nextDirectionOnSameCell(Point2D centerOfMonster, Point2D centerOfFigure) {
        double dx = Math.abs(centerOfFigure.getX() - centerOfMonster.getX());
        double dy = Math.abs(centerOfFigure.getY()) - centerOfMonster.getY();

        if (dx < dy) {
            if (centerOfFigure.getY() < centerOfMonster.getY()) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        }

        if (dy <= dx) {
            if (centerOfFigure.getX() < centerOfMonster.getX()) {
                return Direction.LEFT;
            } else {
                return Direction.RIGHT;
            }
        }
        return null;
    }

    public boolean checkIntersectWall(Point2D p1, Point2D p2) {
        Point2D higher = (p1.getY() < p2.getY()) ? p1 : p2;
        Point2D lower = (higher == p1) ? p2 : p1;

        double distance = p1.distance(p2);
        double cos = (lower.getX() - higher.getX()) / distance;
        double sin = (lower.getY() - higher.getY()) / distance;
        double x = higher.getX();
        double y = higher.getY();
        double k = gameScreen.getComponentSize() / 2d;
        while (y <= lower.getY()) {
            if (walls.get(new Point2D((int) (x / gameScreen.getComponentSize()), (int) (y / gameScreen.getComponentSize()))) != null) {
                return true;
            }
            x += k * cos;
            y += k * sin;
        }

        return false;

    }

    public List<Figure> getFigureList() {
        List<Figure> list = new ArrayList<>();
        for (String fir : figures.keySet()) {
            list.add(figures.get(fir));
        }
        return list;
    }

    public boolean lookupFigure(Gate gate) {
        for (String fir : figures.keySet()) {
            if (Math.abs(figures.get(fir).getX() - gate.getX()) < gameScreen.getComponentSize() / 2d) {
                return true;
            }
        }
        return false;
    }

    public int countOfMonster() {
        return monsters.size();
    }

    public HashMap<String, Figure> getFigures() {
        return figures;
    }

    public HashMap<Point2D, Wall> getWalls() {
        return walls;
    }

    public HashMap<String, Monster> getMonsters() {
        return monsters;
    }

    public List<Boom> getBooms() {
        return booms;
    }
}
