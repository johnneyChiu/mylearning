package personal.xiaoq.realtime.sink;

import java.io.PrintStream;
import java.io.Serializable;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2019-11-13 16:30
 * @Version: V1.0.0
 */
public abstract class  MyPrintSinkOutWriter<IN> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final boolean STD_OUT = false;
    private static final boolean STD_ERR = true;

    private final boolean target;
    private transient PrintStream stream;
    private final String sinkIdentifier;
    private transient String completedPrefix;


    public MyPrintSinkOutWriter() {
        this("", STD_OUT);
    }

    public MyPrintSinkOutWriter(final boolean stdErr) {
        this("", stdErr);
    }

    public MyPrintSinkOutWriter(final String sinkIdentifier, final boolean stdErr) {
        this.target = stdErr;
        this.sinkIdentifier = (sinkIdentifier == null ? "" : sinkIdentifier);
    }

    public void open(int subtaskIndex, int numParallelSubtasks) {
        // get the target stream
        stream = target == STD_OUT ? System.out : System.err;

        completedPrefix = sinkIdentifier;

        if (numParallelSubtasks > 1) {
            if (!completedPrefix.isEmpty()) {
                completedPrefix += ":";
            }
            completedPrefix += (subtaskIndex + 1);
        }

        if (!completedPrefix.isEmpty()) {
            completedPrefix += "> ";
        }
    }

    public  void write(IN record){
        stream.println(completedPrefix + serialize(record));
    }

    public abstract String serialize(IN record);


    @Override
    public String toString() {
        return "Print to " + (target == STD_OUT ? "System.out" : "System.err");
    }

}
