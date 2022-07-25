// Generated by Dagger (https://dagger.dev).
package com.example.examen1ev.data.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RoomModule_GetAssetDBFactory implements Factory<String> {
  @Override
  public String get() {
    return getAssetDB();
  }

  public static RoomModule_GetAssetDBFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String getAssetDB() {
    return Preconditions.checkNotNullFromProvides(RoomModule.INSTANCE.getAssetDB());
  }

  private static final class InstanceHolder {
    private static final RoomModule_GetAssetDBFactory INSTANCE = new RoomModule_GetAssetDBFactory();
  }
}