package com.minecolonies.api.blocks;

public abstract class AbstractBlockSilo<B extends AbstractBlockSilo<B>> extends AbstractBlockMinecoloniesContainer<B> {

    public AbstractBlockSilo(final Properties props) { super(props.noOcclusion());}
}
