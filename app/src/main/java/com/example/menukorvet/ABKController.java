package com.example.menukorvet;

public class ABKController {

    private NumberABK ABK;

    public int getABK() {
        return ABK.getId();
    }

    private enum NumberABK {
        ABK1 {
            @Override
            int getId() {
                return 1;
            }
        },
        ABK3 {
            @Override
            int getId() {
                return 2;
            }
        };

        abstract int getId();
    }
}
