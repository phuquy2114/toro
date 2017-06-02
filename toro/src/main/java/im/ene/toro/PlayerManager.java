/*
 * Copyright (c) 2017 Nam Nguyen, nam@ene.im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.ene.toro;

import android.support.annotation.NonNull;
import im.ene.toro.widget.Container;
import java.util.Collection;

/**
 * @author eneim | 5/31/17.
 *
 *         Logic: collect all Players those "wantsToPlay()", then internally decide if we allow
 *         each
 *         of them to play or not.
 */

public interface PlayerManager {

  String TAG = "ToroLib:PlayerManager";

  void updatePlayback(@NonNull Container container, @NonNull Selector selector);

  // Call before player starts playback
  boolean attachPlayer(Player player);

  // Call after player pauses or stops playback
  boolean detachPlayer(Player player);

  boolean manages(Player player);

  @NonNull Collection<Player> getPlayers();
}
