//////////////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2013-2017, João Vitor Verona Biazibetti - All Rights Reserved           /
//                                                                                       /
// This program is free software: you can redistribute it and/or modify                  /
// it under the terms of the GNU General Public License as published by                  /
// the Free Software Foundation, either version 3 of the License, or                     /
// (at your option) any later version.                                                   /
//                                                                                       /
// This program is distributed in the hope that it will be useful,                       /
// but WITHOUT ANY WARRANTY; without even the implied warranty of                        /
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the                          /
// GNU General Public License for more details.                                          /
//                                                                                       /
// You should have received a copy of the GNU General Public License                     /
// along with this program. If not, see <http://www.gnu.org/licenses/>.                  /
//                                                                                       /
// https://www.github.com/BloodShura                                                     /
//////////////////////////////////////////////////////////////////////////////////////////

package com.github.bloodshura.x.venus.library.crypto;

import com.github.bloodshura.x.collection.tuple.Pair;
import com.github.bloodshura.x.cryptography.Decrypter;
import com.github.bloodshura.x.cryptography.Encrypter;
import com.github.bloodshura.x.venus.library.VenusLibrary;

public class CryptoLibrary extends VenusLibrary {
  public CryptoLibrary() {
    this(new CryptographyMap().registerDefaults());
  }

  public CryptoLibrary(CryptographyMap map) {
    for (Pair<String, Object> pair : map) {
      if (pair.getRight() instanceof Encrypter) {
        add(new EncryptFunction(pair.getLeft(), (Encrypter) pair.getRight()));
      }

      if (pair.getRight() instanceof Decrypter) {
        add(new DecryptFunction("un" + pair.getLeft(), (Decrypter) pair.getRight()));
      }
    }
  }
}