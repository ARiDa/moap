package arida.ufc.br.moap.rec.location.beans;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author igobrilhante
 * @param <U>
 * @param <I>
 */
public class RecommendationSet<U, I> {

    private Map<U, Map<I, Double>> dataset;

    /**
     *
     */
    public RecommendationSet() {
        this.dataset = new HashMap<U, Map<I, Double>>();
    }

    /**
     *
     * @param dataset
     */
    public RecommendationSet(Map<U, Map<I, Double>> dataset) {
        this.dataset = dataset;
    }

    /**
     *
     * @return
     */
    public Map<U, Map<I, Double>> getDataset() {
        return this.dataset;
    }

    /**
     *
     * @param dataset
     */
    public void setDataset(Map<U, Map<I, Double>> dataset) {
        this.dataset = dataset;
    }

    /**
     * @param user
     * @param items
     */
    public void addUser(U user, Map<I, Double> items) {
        this.dataset.put(user, items);
    }

    /**
     * @param user
     */
    public void addUser(U user) {
        Map<I, Double> items = new HashMap<I, Double>();
        this.dataset.put(user, items);
    }

    /**
     * @param user
     * @param items
     */
    public void addUserItems(U user, Map<I, Double> items) {
        this.dataset.get(user).putAll(items);
    }

    /**
     * @param user
     * @param item
     * @param rating
     */
    public void addUserItem(U user, I item, Double rating) {
        this.dataset.get(user).put(item, rating);
    }

    /**
     * @param user
     * @param item
     * @param rating
     */
    public void updateUserItem(U user, I item, Double rating) {
        this.dataset.get(user).put(item, rating);
    }

    /**
     * @param user
     * @param item
     * @return
     */
    public double getRating(U user, I item) {
        return this.dataset.get(user).get(item);
    }

    /**
     *
     * @param user
     * @param item
     * @param rating
     * @return
     */
    public double setRating(U user, I item, double rating) {
        return this.dataset.get(user).put(item, rating);
    }

    /**
     *
     * @param user
     * @param item
     * @return
     */
    public boolean contains(U user, I item) {
        if (this.dataset.get(user).containsKey(item)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean containsUser(U user) {
        if (this.dataset.containsKey(user)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Set<U> getUsers() {
        return this.dataset.keySet();
    }

    /**
     *
     * @return
     */
    public Set<I> getItems() {
        Set<I> items = new HashSet<I>();

        for (U user : this.dataset.keySet()) {
            Set<I> user_items = this.dataset.get(user).keySet();
            for (I item : user_items) {
                items.add(item);
            }
        }

        return items;
    }

    /**
     *
     * @param user
     * @return
     */
    public Set<I> getItems(U user) {
        return this.dataset.get(user).keySet();
    }

    /**
     *
     * @param user
     * @return
     */
    public Map<I, Double> getItemRatings(U user) {
        return this.dataset.get(user);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "";
        for (U user : this.getUsers()) {
            s += user + "{\n";
            for (I item : this.getItems(user)) {
                s += "\t" + item + "\t" + this.getRating(user, item) + "\n";
            }
            s += "}\n";
        }

        return s;
    }

    /**
     *
     * @return
     */
    public double[][] getUserItem() {
        double[][] matrix = null;

        return matrix;
    }

    /**
     *
     * @return
     */
    public int getUserCount() {
        int count = this.dataset.size();
        return count;
    }

    /**
     *
     * @return
     */
    public int getItemCount() {
        int count = 0;
        Set<I> items = new HashSet<I>();

        for (U user : this.dataset.keySet()) {
            Set<I> user_items = this.dataset.get(user).keySet();
            for (I item : user_items) {
                items.add(item);
            }
        }
        count = items.size();
        return count;
    }
}
