public class Task4Test {

    /**
     * Pool that block when it has not any items or it full
     */
    public class BlockingObjectPool {

        private Object[] collection;
        private int count;

        /**
         * Creates filled pool of passed size
         *
         * @param size of pool
         */
        public BlockingObjectPool(int size) {
            collection = new Object[size];
            count = 0;
        }

        /**
         * Gets object from pool or blocks if pool is empty
         *
         * @return object from pool
         */
        public Object get() {
            count--;
            return collection[count];
        }

        /**
         * Puts object to pool or blocks if pool is full
         *
         * @param object to be taken back to pool
         */
        public void put(Object object) {
            collection[count] = object;
            count++;
        }
    }

}
