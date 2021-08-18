package com.minecolonies.api.blocks;

public abstract class AbstractBlockSilo<B extends AbstractBlockSilo<B>> extends AbstractBlockMinecolonies<B> {

    public AbstractBlockSilo(final Properties props) { super(props.noOcclusion());}
}
