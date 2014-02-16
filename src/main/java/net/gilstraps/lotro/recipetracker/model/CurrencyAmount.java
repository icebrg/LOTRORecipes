package net.gilstraps.lotro.recipetracker.model;

import org.w3c.dom.ranges.RangeException;

/**
 * TODO
 */
public class CurrencyAmount {

    public static final long SILVER = 100;
    public static final long GOLD = 100 * SILVER;
    private long total;

    public CurrencyAmount(long total) {
        this.total = total;
    }

    public CurrencyAmount(long silvers, long coppers) {
        if (silvers < 0 && coppers > 0 || silvers > 0 && coppers < 0) {
            throw new IllegalArgumentException("Arguments must be all negative or all non-negative");
        }
        if (coppers < -99 || coppers > 99) throw new IllegalArgumentException("When specifying silvers, coppers must be [-99,99]");
        this.total = (silvers * SILVER) + coppers;
    }

    public CurrencyAmount(long golds, long silvers, long coppers) {
        if (golds < 0 && (silvers > 0 || coppers > 0)) {
            throw new IllegalArgumentException("When golds(" + golds + ") are negative, silver(" + silvers + ") and copper(" + coppers + ") must be negative or zero.");
        }
        if (golds > 0 && (silvers < 0 || coppers < 0)) {
            throw new IllegalArgumentException("When golds(" + golds + ") are positive, silver(" + silvers + ") and copper(" + coppers + ") must be positive or zero.");
        }
        if (silvers < -99 || silvers > 99) throw new IllegalArgumentException("When specifying golds, silvers must be [-99,99]");
        if (coppers < -99 || coppers > 99) throw new IllegalArgumentException("When specifying silvers, coppers must be [-99,99]");
        this.total = (golds * GOLD) + (silvers * SILVER) + coppers;
    }

    public long getTotal() {
        return total;
    }

    public long getGolds() {
        return total / GOLD;
    }

    public long getSilvers() {
        long s = total % GOLD;
        return s / SILVER;
    }

    public long getCoppers() {
        long c = total % GOLD % SILVER;
        return c;
    }

    public String toString() {
        String str = "";
        long g = getGolds();
        if (g != 0) str += g + "g,";
        long s = getSilvers();
        if (s != 0 || g != 0) str += s + "s,";
        long c = getCoppers();
        str += c + "c";
        return str;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyAmount)) return false;

        final CurrencyAmount that = (CurrencyAmount) o;

        if (total != that.total) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (total ^ (total >>> 32));
    }
}
