package database;

/**
 * Created by haritha on 9/6/18.
 */

public class AppDbSchema {
    public static final class SummaryTable {
        public static final String NAME = "SummaryTable";

        public static final class Cols {
            public static final String TOTALPOINTS = "totalPoints";
            public static final String DATE = "date";
        }

    }

    public static final class ProductTable {
        public static final String NAME = "ProductTable";

        public static final class Cols {
            public static final String PRODUCTNAME = "productName";
            public static final String PRODUCTPOINT = "productPoint";
            public static final String PRODUCTPOINTSUM = "productPointSum";
            public static final String PRODUCTIMAGE = "productImage";
            public static final String PRODUCTID = "productId";

        }

    }
}