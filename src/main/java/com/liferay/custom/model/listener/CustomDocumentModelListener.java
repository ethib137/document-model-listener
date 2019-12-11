package com.liferay.custom.model.listener;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;
import com.liferay.portal.security.audit.event.generators.util.AuditMessageBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author evanthibodeau
 */
@Component(
	immediate = true,
	service = ModelListener.class
)
public class CustomDocumentModelListener extends BaseModelListener<DLFileEntry> {

	@Override
	public void onAfterCreate(DLFileEntry dlFileEntry)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage =
				AuditMessageBuilder.buildAuditMessage(
					EventTypes.ADD, DLFileEntry.class.getName(),
					dlFileEntry.getFileEntryId(), null);

			_auditRouter.route(auditMessage);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference
	private AuditRouter _auditRouter;

}