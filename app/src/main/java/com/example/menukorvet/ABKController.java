package com.example.menukorvet;

import java.util.Objects;

public class ABKController {

    private int abkIndex;
    private static ABKController abkController;

    public void abkNextInstance() {

        abkIndex = getNextAbkIndex();
    }

    public NumberABK getNextABK() {

        return NumberABK.values()[getNextAbkIndex()];
    }

    private int getNextAbkIndex() {

        int abkLastIndex = NumberABK.values().length - 1;

        if (abkIndex == abkLastIndex) {
            return 0;
        }

        return abkIndex + 1;
    }

    public NumberABK getABK() {

        return NumberABK.values()[abkIndex];
    }

    private ABKController() {}

    public static ABKController getInstance() {
        if (Objects.isNull(abkController)) {
            abkController = new ABKController();
            abkController.abkIndex = -1;
        }

        return abkController;
    }

    public enum NumberABK {
        ABK1 {
            @Override
            int getId() {
                return 1;
            }

            @Override
            int getName() {
                return R.string.text_main_abk1;
            }
        },
        ABK3 {
            @Override
            int getId() {
                return 2;
            }

            @Override
            int getName() {
                return R.string.text_main_abk3;
            }
        };

        abstract int getId();
        abstract int getName();
    }
}
