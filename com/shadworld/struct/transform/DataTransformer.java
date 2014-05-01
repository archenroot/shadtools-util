package com.shadworld.struct.transform;

public abstract class DataTransformer<T>
  implements DataTransform<T>
{
  public abstract T transform(T paramT);
}

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.struct.transform.DataTransformer
 * JD-Core Version:    0.6.2
 */