/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package network.misq.network.p2p.services.data.storage.auth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import network.misq.network.p2p.services.data.NetworkData;
import network.misq.network.p2p.services.data.storage.MetaData;

@EqualsAndHashCode
@Getter
public class MockAuthenticatedPayload implements AuthenticatedPayload {
    private final NetworkData networkData;

    public MockAuthenticatedPayload(NetworkData networkData) {
        this.networkData = networkData;
    }

    @Override
    public MetaData getMetaData() {
        return networkData.getMetaData();
    }

    @Override
    public boolean isDataInvalid() {
        return networkData.isDataInvalid();
    }
}
