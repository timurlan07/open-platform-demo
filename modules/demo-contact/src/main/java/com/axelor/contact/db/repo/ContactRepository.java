/*
 * Axelor Business Solutions
 *
 * Copyright (C) 2005-2023 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.axelor.contact.db.repo;

import com.axelor.common.ObjectUtils;
import com.axelor.contact.db.Contact;
import java.util.Map;

public class ContactRepository extends AbstractContactRepository {

  @Override
  public Map<String, Object> populate(Map<String, Object> json, Map<String, Object> context) {
    if (!context.containsKey("json-enhance")) {
      return json;
    }
    try {
      Long id = (Long) json.get("id");
      Contact contact = find(id);
      json.put(
          "address",
          ObjectUtils.isEmpty(contact.getAddresses()) ? null : contact.getAddresses().get(0));
      json.put("hasImage", contact.getImage() != null);
    } catch (Exception e) {
    }

    return json;
  }
}
