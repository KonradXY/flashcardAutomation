//package coursera._01_quickfind;
//
//import org.junit.jupiter.api.Test;
//
//import coursera._01_quickfind.DynamicConnectivity;
//import coursera._01_quickfind.QuickFind;
//import coursera._01_quickfind.QuickUnion;
//import coursera._01_quickfind.QuickUnionFind;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DynamicConnectivityTest {
//
//    public static final int SIZE = 10;
//    private static final int[] CONNECTED_3_AND_7 = new int[]{0, 9, 6, 5, 4, 2, 6, 1, 0, 5};
//
//    @Test
//    public void testDynamicConnectivityWithQuickFind() {
//        testDynamicConnectivity(new QuickFind(SIZE));
//        testDynamicConnectivityReverseOrder(new QuickFind(SIZE));
//    }
//
//    @Test
//    public void testDynamicConnectivityWithQuickUnion() {
//        testDynamicConnectivity(new QuickUnion(SIZE));
//        testDynamicConnectivityReverseOrder(new QuickUnion(SIZE));
//        testNotConnected(new QuickUnion(CONNECTED_3_AND_7));
//    }
//
//    @Test
//    public void testDynamicConnectivityWithQuickUnionFind() {
//        testDynamicConnectivity(new QuickUnionFind(SIZE));
//        testDynamicConnectivityReverseOrder(new QuickUnionFind(SIZE));
//        testNotConnected(new QuickUnionFind(CONNECTED_3_AND_7));
//    }
//
//
//
//    private void testDynamicConnectivity(DynamicConnectivity dynamicConnectivity) {
//        for (int i = 0; i<dynamicConnectivity.array.length-2; i++) {
//            assertFalse(dynamicConnectivity.connected(i, i+1));
//            dynamicConnectivity.union(i, i+1);
//            assertTrue(dynamicConnectivity.connected(i, i+1));
//        }
//    }
//
//    private void testDynamicConnectivityReverseOrder(DynamicConnectivity dynamicConnectivity) {
//        for (int i = dynamicConnectivity.array.length-1; i >0; i--) {
//            assertFalse(dynamicConnectivity.connected(i, i-1));
//            dynamicConnectivity.union(i, i-1);
//            assertTrue(dynamicConnectivity.connected(i, i-1));
//        }
//    }
//
//    private void testNotConnected(DynamicConnectivity dynamicConnectivity) {
//        assertTrue(dynamicConnectivity.connected(3,7));
//        assertFalse(dynamicConnectivity.connected(0,9));
//
//        dynamicConnectivity.union(0,9);
//        assertTrue(dynamicConnectivity.connected(0,9));
//    }
//
//}