package ru.ads.queue;

public class QueueUtils {

    public static <T> void rotateQueue(Queue<T> queue, int n) {
        if (queue.size() == 0) {
            return;
        }

        for (int i = 0; i < n % queue.size(); i++) {
            T element = queue.dequeue();
            queue.enqueue(element);
        }
    }
}
